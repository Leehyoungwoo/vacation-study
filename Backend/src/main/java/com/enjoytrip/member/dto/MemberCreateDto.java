package com.enjoytrip.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberCreateDto {

    @NotBlank
    @Size(min = 6, max = 30)
    @JsonProperty("email")
    private final String username;

    @NotBlank
    @Size(max = 20)
    private final String name;

    @NotBlank
    @Size(max = 10)
    private final String nickname;

    @NotBlank
    @Size(min = 6, max = 15)
    private String password;
}
