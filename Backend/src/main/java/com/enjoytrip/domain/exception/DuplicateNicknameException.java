package com.enjoytrip.domain.exception;

public class DuplicateNicknameException extends RuntimeException{

    public DuplicateNicknameException(String message) {
        super(message);
    }
}
