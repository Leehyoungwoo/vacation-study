package com.enjoytrip.domain.exception;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(String message) {
        super(message);
    }

    public BoardNotFoundException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
