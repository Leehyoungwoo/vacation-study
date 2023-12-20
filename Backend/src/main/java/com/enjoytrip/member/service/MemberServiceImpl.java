package com.enjoytrip.member.service;

import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.mapper.MemberMapper;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.enjoytrip.domain.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return memberRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    @Override
    public String getNicknameByMember(Member member) {
        return member.getNickname();
    }

    @Override
    @Transactional
    public void joinMember(MemberCreateDto memberCreateDto) {
        Member newMember = MemberMapper.toEntity(memberCreateDto);
        memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public void updateNickName(Long id, String newNickname) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.changeNickname(newNickname);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String newPassword) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.changePassword(newPassword);
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.markAsDeleted();
    }
}
