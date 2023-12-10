package com.enjoytrip.member.mapper;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.dto.MemberCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberMapper {

    public static Member toEntity(MemberCreateDto memberCreateDto) {
        return Member.builder()
                     .username(memberCreateDto.getUsername())
                     .password(memberCreateDto.getPassword())
                     .name(memberCreateDto.getName())
                     .nickname(memberCreateDto.getNickname())
                     .role(Role.USER)
                     .isDeleted(false)
                     .build();
    }
}
