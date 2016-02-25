package com.vajda.spockExamples.datadriven;

import spock.lang.Specification
import spock.lang.Unroll

import com.fasterxml.jackson.databind.ObjectMapper
import com.vajda.spockExamples.datadriven.Expression.Operator

public class ExpressionSpec extends Specification {

    def objectMapper
    
    def setup() {
        objectMapper = new ObjectMapper()
    }
    
    @Unroll
    def "serialize #op operator to enumeration #enumOperator"() {
        given:
        def expression = """{"left":"a","operator":"$op","right":"22"}"""
        
        when:
        def result = objectMapper.readValue(expression, Expression)
        
        then:
        result.operator == enumOperator
        
        where:
        enumOperator | op
        Operator.GE  | ">="
        Operator.LE  | "<="
        Operator.NE  | "<>"
    }
    
    @Unroll("serialize #op operator to enumeration #enumOperator")
    def "data pipe approach"() {
        given:
        def expression = """{"left":"a","operator":"$op","right":"22"}"""
        
        when:
        def result = objectMapper.readValue(expression, Expression)
        
        then:
        result.operator == enumOperator
        
        where:
        enumOperator << [Operator.EQ, Operator.GT, Operator.LT]
        op << ["=", ">", "<"]
    }
}
