package com.enjoytrip.domain.exception;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
