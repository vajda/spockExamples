package com.vajda.spockExamples.interactions

import org.slf4j.Logger

import spock.lang.IgnoreRest
import spock.lang.Specification
import spock.lang.Unroll


class PublisherSpec extends Specification {

    def logger = Mock(Logger)
    Worker worker
    def publisher
    
    def setup() {
        worker = Mock()
        publisher = new Publisher(logger, worker)
    }
    
    def "subscriber should receive published event - exact object"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * sub1.receive("event1")
    }

    def "subscriber should receive published event - object type"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * sub1.receive(_ as String)
    }
    
    def "subscriber should receive published event - wrong object type"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * sub1.receive(_ as Integer)
    }
    
    def "subscriber should receive published event - anything"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * sub1.receive(_)
    }
    
    def "subscriber should receive published event - up to 3 times"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        
        then:
        (_..3) * sub1.receive(_)
    }
    
    def "subscriber should receive published event - at least 3 times"() {
        given:
        def sub1 = Mock(Subscriber)
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        
        then:
        (3.._) * sub1.receive(_)
    }
    
    def "workA should be called when subscriber returns true"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive("event1") >> true
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * worker.doWorkA()
    }
    
    def "workB should be called when subscriber returns false"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive("event1") >> false
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * worker.doWorkB()
    }
    
    @Unroll
    def "#method should be called when subscriber returns #value"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive("event1") >> value
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * worker."$method"()
        
        where:
        method    | value
        "doWorkA" | true
        "doWorkB" | false
    }
    
    def "workA should be called when subscriber returns true - anything"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive(_) >> true
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish(34)
        
        then:
        2 * worker.doWorkA()
    }
    
    def "workA should be called when subscriber returns true - closure"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive({ it.startsWith("event") }) >> true
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        publisher.publish("some other text")
        publisher.publish(null)
        publisher.publish(34)
        
        then:
        2 * worker.doWorkA()
    }
    
    def "workA should be called when subscriber returns true - strict"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive({ it.startsWith("event") }) >> true
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        publisher.publish("some other text")
        publisher.publish(null)
        publisher.publish(34)
        
        then:
        2 * worker.doWorkA()
        0 * worker._
    }
    
    def "workA should be called when subscriber returns true - stub return type"() {
        given:
        def sub1 = Mock(Subscriber) {
            receive(_) >> { args -> args[0] == "event1" ? true : false }
        }
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        publisher.publish("some other text")
        publisher.publish(null)
        publisher.publish(34)
        
        then:
        1 * worker.doWorkA()
    }
    
    def "exception example"() {
        given:
        def e = new RuntimeException()
        def sub1 = Mock(Subscriber) {
            receive(_) >> { throw e }
        }
        def sub2 = Mock(Subscriber) {
            receive("event1") >> true
        }
    
        publisher.subscribers = [sub1, sub2]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * worker.doWorkA()
        0 * worker.doWorkB()
        1 * logger.error("Error happened", e);
    }
    
    def "multiple return types"() {
        given:
        def e = new RuntimeException()
        def sub1 = Mock(Subscriber) {
            receive(_) >>> [true, true] >> { throw e } >>> [true, false]
        }
    
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        publisher.publish("event3")
        publisher.publish("event4")
        publisher.publish("event5")
        publisher.publish("event6")
        
        then:
        3 * worker.doWorkA()
        2 * worker.doWorkB()
        1 * logger.error("Error happened", e);
    }
    
    def "multiple return types regex"() {
        given:
        def e = new RuntimeException()
        def sub1 = Mock(Subscriber) {
            receive(_) >>> [true, true] >> { throw e } >>> [true, false]
        }
    
        publisher.subscribers = [sub1]
        
        when:
        publisher.publish("event1")
        publisher.publish("event2")
        publisher.publish("event3")
        publisher.publish("event4")
        publisher.publish("event5")
        publisher.publish("event6")
        
        then:
        5 * worker./doWork.*/()
        1 * logger.error("Error happened", e);
    }
    
    def "order matters"() {
        given:
        def sub1 = Mock(Subscriber)
        def sub2 = Mock(Subscriber)
        def sub3 = Mock(Subscriber)
    
        publisher.subscribers = [sub1, sub2, sub3]
        
        when:
        publisher.publish("event1")
        
        then:
        1 * sub1.receive("event1")
        
        then:
        1 * sub3.receive("event1")
        
        then:
        1 * sub2.receive("event1")
    }
}
