package com.enjoytrip.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class MemberCreateDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String nickname;
}
