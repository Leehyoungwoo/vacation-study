package com.enjoytrip.member.service;

import com.enjoytrip.domain.exception.DuplicatedNicknameException;
import com.enjoytrip.domain.exception.MemberAlreadyExistsException;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
import com.enjoytrip.member.mapper.MemberMapper;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void joinMember(MemberCreateDto memberCreateDto) {
        validateDuplicateUsername(memberCreateDto.getUsername());
        validateDuplicateNickname(memberCreateDto.getNickname());
        Member newMember = MemberMapper.toEntity(memberCreateDto);
        memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public void updateNickName(Long id, UpdateNicknameDto updateNicknameDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.changeNickname(updateNicknameDto.getNewNickname());
    }

    @Override
    @Transactional
    public void updatePassword(Long id, MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        String newPassword = memberPasswordUpdateDto.getNewPassword();
        member.changePassword(newPassword);
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.markAsDeleted();
    }

    private void validateDuplicateUsername(String username) {
        memberRepository.findByUsername(username)
                .ifPresent(m -> {
                    throw new MemberAlreadyExistsException(MEMBER_ALREADY_EXISTS);
                });
    }

    private void validateDuplicateNickname(String nickname) {
        memberRepository.findMemberByNickname(nickname)
                .ifPresent(m -> {
                    throw new DuplicatedNicknameException(MEMBER_DUPLICATED_NICKNAME);
                });
    }
}
