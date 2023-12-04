package com.enjoytrip.member.service;

import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.exception.MemberAlreadyExistsException;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void joinMember(MemberCreateDto memberCreateDto) {
        // 중복 아이디 검증
        validateDuplicateMember(memberCreateDto.getUsername());
        // 비밀번호 암호화 후 저장
        memberCreateDto
                .setPassword(passwordEncoder
                .encode(memberCreateDto
                        .getPassword()));
        memberRepository.save(Member.toEntity(memberCreateDto));
    }

    @Transactional
    public void validateDuplicateMember(String username) {
        memberRepository.findByUsername(username)
                .ifPresent(m -> {
                    throw new MemberAlreadyExistsException("이미 존재하는 회원입니다.");
                });
    }
}
