package com.enjoytrip.member.service;

import com.enjoytrip.domain.exception.DuplicatedNicknameException;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    void joinMember(MemberCreateDto memberCreateDto) throws DuplicatedNicknameException;

    Member findMemberById(Long id);

    void deleteMember(Long id);

    void updateNickName(Long id, UpdateNicknameDto updateNicknameDto);

    void updatePassword(Long id, MemberPasswordUpdateDto memberPasswordUpdateDto);
}
