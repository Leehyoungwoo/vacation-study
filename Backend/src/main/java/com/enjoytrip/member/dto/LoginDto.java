package com.enjoytrip.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginDto {

    private final String username;
    private final String password;
}
