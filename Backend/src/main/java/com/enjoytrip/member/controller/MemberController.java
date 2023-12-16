package com.enjoytrip.member.controller;

import com.enjoytrip.auth.AuthService;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberInfoDto;
import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
import com.enjoytrip.member.mapper.MemberMapper;
import com.enjoytrip.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;
    private final MemberService memberService;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoDto getMemberInfo(@AuthenticationPrincipal Member principal) {
        Long id = principal.getId();
        Member member = memberService.findMemberById(id);
        return MemberMapper.toMemberInfoDto(member);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void joinMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {
        memberService.joinMember(memberCreateDto);
    }

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public void updateNickname(@AuthenticationPrincipal Member principal,
                               @RequestBody @Valid UpdateNicknameDto updateNicknameDto) {
        Long id = principal.getId();
        memberService.updateNickName(id, updateNicknameDto);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authenticatePassword(#principal.getId(), #memberPasswordUpdateDto.getCurrentPassword())")
    public void updatePassword(@AuthenticationPrincipal Member principal,
                               @RequestBody @Valid MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Long id = principal.getId();
        memberService.updatePassword(id, memberPasswordUpdateDto);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@AuthenticationPrincipal Member principal) {
        Long id = principal.getId();
        memberService.deleteMember(id);
    }
}
