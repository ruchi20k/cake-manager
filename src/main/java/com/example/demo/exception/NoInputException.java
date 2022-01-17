package com.example.demo.exception;

public class NoInputException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NoInputException() {
	super();
    }

    public NoInputException(String customMessage) {
	super(customMessage);
    }
}
