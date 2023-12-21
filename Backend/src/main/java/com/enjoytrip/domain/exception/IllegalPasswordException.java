package com.enjoytrip.domain.exception;

public class IllegalPasswordException extends RuntimeException{

    public IllegalPasswordException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
