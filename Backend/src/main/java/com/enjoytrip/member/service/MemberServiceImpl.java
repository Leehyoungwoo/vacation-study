package com.enjoytrip.member.service;

import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
import com.enjoytrip.domain.exception.DuplicateNicknameException;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.exception.MemberAlreadyExistsException;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.mapper.MemberMapper;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void joinMember(MemberCreateDto memberCreateDto) {
        // 중복 아이디 검증
        validateDuplicateMember(memberCreateDto.getUsername());
        // 닉네임 중복 검증
        validateDuplicateNickname(memberCreateDto.getNickname());
        memberRepository.save(MemberMapper.toEntity(memberCreateDto));
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        memberRepository.deleteMember(id);
    }

    @Override
    @Transactional
    public void updateNickName(Long id, UpdateNicknameDto updateNicknameDto) {
        memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        memberRepository.updateNickname(id, updateNicknameDto.getNewNickname());
    }

    @Transactional
    @Override
    public void updatePassword(Long id, MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));

        String currentPassword = memberPasswordUpdateDto.getCurrentPassword();
        String newPassword = memberPasswordUpdateDto.getNewPassword();

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new BadCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }
        memberRepository.updatePassword(id, newPassword);
    }

    private void validateDuplicateMember(String username) {
        memberRepository.findByUsername(username)
                .ifPresent(m -> {
                    throw new MemberAlreadyExistsException("이미 존재하는 회원입니다.");
                });
    }

    private void validateDuplicateNickname(String nickname) {
        memberRepository.findMemberByNickname(nickname)
                .ifPresent(m -> {
                    throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
                });
    }
}
