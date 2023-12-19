package com.enjoytrip.domain.exception;

public class BoardLikeNotFoundException extends RuntimeException {

    public BoardLikeNotFoundException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
