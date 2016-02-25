package com.vajda.spockExamples.datadriven

import spock.lang.Specification
import spock.lang.Unroll


class XmlSpec extends Specification {

    @Unroll
    def "#data.description"() {
        expect:
        first + second == result
        
        where:
        data << getDataFromFile("src/test/resources/testData.xml")
        first = data.first
        second = data.second
        result = data.result
    }
    
    def getDataFromFile(fileName) {
        def tests = new XmlSlurper().parse(fileName)
        def data = []
        tests.testVariation.each {
            data << [description:it.@description, first:it.first.text() as int, second:it.second.text() as int, result:it.result.text() as int]
        }
        data
    }
}
