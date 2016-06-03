package com.vajda.spockExamples.interactions

import spock.lang.Specification


class DataOperationsSpec extends Specification {

    def sqlExecutor
    def dataOperations
    
    def setup() {
        sqlExecutor = Mock(SqlExecutor)
        dataOperations = Spy(DataOperations, constructorArgs:[sqlExecutor])
    }

    def "test getData method"() {
        given:
        def rawData = Mock(List)
        sqlExecutor.execute("select * from some_table;") >> rawData
        def transformedData = [[1, 3, 5], [2, 3, 8, 2]]
        dataOperations.transformData(rawData) >> transformedData

        when:
        def result = dataOperations.getData("select * from some_table;", 5)

        then:
        result == [[5], [8, 9]]
    }
}
