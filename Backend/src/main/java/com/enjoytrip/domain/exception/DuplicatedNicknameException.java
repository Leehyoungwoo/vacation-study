package com.enjoytrip.domain.exception;

public class DuplicatedNicknameException extends RuntimeException {

    public DuplicatedNicknameException(String message) {
        super(message);
    }

    public DuplicatedNicknameException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
