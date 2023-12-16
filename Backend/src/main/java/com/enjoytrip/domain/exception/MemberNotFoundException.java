package com.enjoytrip.domain.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberNotFoundException extends UsernameNotFoundException {

    public MemberNotFoundException(String msg) {
        super(msg);
    }

    public MemberNotFoundException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

    public MemberNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MemberNotFoundException(ExceptionMessage msg, Throwable cause) {
        super(msg.getMessage(), cause);
    }
}
