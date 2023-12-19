package com.enjoytrip.member.mapper;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberInfoDto;
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

    public static MemberInfoDto toMemberInfoDto(Member member) {
        return MemberInfoDto.builder()
                            .username(member.getUsername())
                            .nickname(member.getNickname())
                            .name(member.getName())
                            .role(member.getRole().name())
                            .build();
    }
}
