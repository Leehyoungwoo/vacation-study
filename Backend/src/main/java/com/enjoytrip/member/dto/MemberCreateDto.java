package com.enjoytrip.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class MemberCreateDto {

    @NotBlank
    private final String username;

    @Setter
    @NotBlank
    private String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String nickname;
}
