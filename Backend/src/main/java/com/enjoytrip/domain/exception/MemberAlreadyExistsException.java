package com.enjoytrip.domain.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String message) {
        super(message);
    }

    public MemberAlreadyExistsException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
