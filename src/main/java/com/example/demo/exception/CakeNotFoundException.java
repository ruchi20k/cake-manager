package com.example.demo.exception;

public class CakeNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CakeNotFoundException() {
	super();
    }

    public CakeNotFoundException(String customMessage) {
	super(customMessage);
    }
}
