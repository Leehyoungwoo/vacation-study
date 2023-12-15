package com.enjoytrip.domain.exception;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(String message) {
        super(message);
    }
}
