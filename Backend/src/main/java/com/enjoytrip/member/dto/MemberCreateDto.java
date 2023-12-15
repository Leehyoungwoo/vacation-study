package com.enjoytrip.member.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberCreateDto {

    @NotBlank
    private final String username;

    @NotBlank
    private String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String nickname;
}
