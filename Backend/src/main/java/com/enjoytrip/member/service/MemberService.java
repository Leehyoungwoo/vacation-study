package com.enjoytrip.member.service;

import com.enjoytrip.board.dto.UpdateNicknameDto;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;

public interface MemberService {
    void joinMember(MemberCreateDto memberCreateDto);

    Member findMemberById(Long id);

    void deleteMember(Long id);

    void updateNickName(Long id, UpdateNicknameDto updateNicknameDto);
}
