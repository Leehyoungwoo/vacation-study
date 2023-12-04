package com.enjoytrip.member.service;

import com.enjoytrip.domain.model.entity.Member;
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
        // 비밀번호 암호화 후 저장
        memberCreateDto
                .setPassword(passwordEncoder
                .encode(memberCreateDto
                        .getPassword()));
        memberRepository.save(Member.toEntity(memberCreateDto));
    }
}
