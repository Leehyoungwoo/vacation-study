package com.enjoytrip.member.service;

import com.enjoytrip.domain.exception.DuplicateNicknameException;
import com.enjoytrip.domain.exception.MemberAlreadyExistsException;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

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
        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        member.delete();
    }

    @Override
    @Transactional
    public void updateNickName(Long id, UpdateNicknameDto updateNicknameDto) {
        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        member.changeNickname(updateNicknameDto.getNewNickname());
    }

    @Transactional
    @Override
    public void updatePassword(Long id, MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        String newPassword = memberPasswordUpdateDto.getNewPassword();
        member.changePassword(newPassword);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Member member = memberRepository.findByUsername(username)
                                              .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        if (member.isDeleted()) {
            throw new UsernameNotFoundException("삭제된 사용자입니다.");
        }

        return member;
    }
}
