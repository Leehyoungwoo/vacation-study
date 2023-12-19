package com.enjoytrip.member.service;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    Member findMemberById(Long id);

    String getNicknameByMember(Member member);

    void joinMember(MemberCreateDto memberCreateDto);

    void updateNickName(Long id, String newNickname);

    void updatePassword(Long id, String newPassword);

    void deleteMember(Long id);
}
