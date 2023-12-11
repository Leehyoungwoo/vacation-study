package com.enjoytrip.member.dto;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Getter
public class MemberCreateDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Convert(converter = PasswordEncoder.class)
    private String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String nickname;
}
