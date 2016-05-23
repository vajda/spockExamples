package com.vajda.spockExamples.basic;


public class SimpleObject {

    private String a;
    private int b;
    private Object c;
    
    public String getA() {
    	System.out.println("method getA() called.");
        return a;
    }
    
    public void setA(String a) {
    	System.out.println("method setA() called.");
        this.a = a;
    }
    
    public int getB() {
    	System.out.println("method getB() called.");
        return b;
    }
    
    public void setB(int b) {
    	System.out.println("method setB() called.");
        this.b = b;
    }
    
    public Object getC() {
    	System.out.println("method getC() called.");
        return c;
    }
    
    public void setC(Object c) {
    	System.out.println("method setC() called.");
        this.c = c;
    }
}
