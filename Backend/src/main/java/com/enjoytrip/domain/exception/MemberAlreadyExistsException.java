package com.enjoytrip.domain.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
