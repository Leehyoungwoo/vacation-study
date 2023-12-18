package com.enjoytrip.jwt.exception;

import com.enjoytrip.domain.exception.ExceptionMessage;

public class MissingTokenException extends RuntimeException{

    public MissingTokenException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}
