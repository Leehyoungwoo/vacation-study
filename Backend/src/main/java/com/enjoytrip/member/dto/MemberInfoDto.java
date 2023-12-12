package com.enjoytrip.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder

public class MemberInfoDto {

    private final String username;
    private final String nickname;
    private final String name;
    private final String role;
}
