package com.enjoytrip.domain.exception;

public class CommentLikeNotFoundException extends RuntimeException {

    public CommentLikeNotFoundException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
