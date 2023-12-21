package com.enjoytrip.util;


public class PasswordValidator {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 15;

    public static boolean isValidPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호가 입력되지 않았습니다.");
        }
        return password.length() >= MIN_LENGTH && password.length() <= MAX_LENGTH;
    }
}
