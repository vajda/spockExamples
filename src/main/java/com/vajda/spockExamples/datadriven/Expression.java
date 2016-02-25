package com.vajda.spockExamples.datadriven;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Expression {

    private String left;
    private Operator operator;
    private String right;

    public enum Operator {
        EQ("="), GT(">"), LT("<"), GE(">="), LE("<="), NE("<>");

        private static Map<String, Operator> operatorBySign = new HashMap<String, Operator>();
        
        private String sign;

        private Operator(String sign) {
            this.sign = sign;
        }
        
        static {
            for (Operator operator : Operator.values()) {
                operatorBySign.put(operator.getSign(), operator);
            }
        }

        @JsonCreator
        public static Operator forValue(String sign) {
            return operatorBySign.get(sign);
        }

        @JsonValue
        public String getSign() {
            return sign;
        }
    }

    public Expression() {

    }

    public Expression(String left, Operator operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}