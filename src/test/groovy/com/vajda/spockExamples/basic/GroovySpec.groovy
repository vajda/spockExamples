package com.vajda.spockExamples.basic

import spock.lang.Specification


class GroovySpec extends Specification {
    
    def "named parameters"() {
        given:
        def simpleObject = new SimpleObject(a:"aValue", b:45, c:new SimpleObject(b:23, c:"stringValue"))
        
        expect:
        simpleObject.a == "aValue"
        simpleObject.b == 45
        simpleObject.c.a == null
        simpleObject.c.b == 23
        simpleObject.c.c == "stringValue"
    }

    def "lists"() {
        given:
        def emptyList = []
        def list = [1, 3, 5]
        
        when:
        list << 7 << 9
        
        then:
        emptyList.empty
        !list.empty
        list.size() == 5
        list.every { it % 2 == 1 }
        list.any { it == 1 }
    }

    def "sets"() {
        given:
        def set1 = [5, 4, 4, 2, 1, 2] as Set
        def set2 = [1, 2, 4, 5] as Set
        
        expect:
        set1 == set2
    }

    def "maps"() {
        given:
        def map1 = [:]
        def map2 = ["a":3, b:5, "c":2]
        
        when:
        map1.val1 = 2
        map1 << [val2:150]
        map1 << [val1:5, val3:8]

        then:
        !map1.empty
        map1.val1 == 5
        map1.x == null
        map2.a == 3
        map2.b == 5
        map2.c == 2
        map1 == [val1:5, val2:150, val3:8]
    }
}
