package com.enjoytrip.member.controller;

import com.enjoytrip.auth.AuthService;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberInfoDto;
import com.enjoytrip.member.dto.MemberPasswordUpdateDto;
import com.enjoytrip.member.dto.UpdateNicknameDto;
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

    @PostMapping("/signup")
    public ResponseEntity<String> joinMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {
        memberService.joinMember(memberCreateDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoDto getMemberInfo(@AuthenticationPrincipal Member principal) {
        Long id = principal.getId();
        Member member = memberService.findMemberById(id);

        return MemberInfoDto.builder()
                            .username(member.getUsername())
                            .nickname(member.getNickname())
                            .name(member.getName())
                            .role(member.getRole().name())
                            .build();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMember(@AuthenticationPrincipal Member principal) {
        Long id = principal.getId();
        memberService.deleteMember(id);
        return "회원 탈퇴가 완료되었습니다.";
    }

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public String updateNickname(@AuthenticationPrincipal Member principal,
                                 @RequestBody @Valid UpdateNicknameDto updateNicknameDto) {
        Long id = principal.getId();
        memberService.updateNickName(id, updateNicknameDto);
        return "닉네임 수정이 완료되었습니다.";
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authenticatePassword(#principal.getId(), #memberPasswordUpdateDto.getCurrentPassword())")
    public String updatePassword(@AuthenticationPrincipal Member principal,
                                 @RequestBody @Valid MemberPasswordUpdateDto memberPasswordUpdateDto) {
        Long id = principal.getId();
        memberService.updatePassword(id, memberPasswordUpdateDto);
        return "비밀번호를 변경하였습니다.";
    }
}
