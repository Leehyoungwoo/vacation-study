package com.enjoytrip.member.controller;

import com.enjoytrip.board.dto.UpdateNicknameDto;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.dto.MemberInfoDto;
import com.enjoytrip.member.service.MemberService;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.enjoytrip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> joinMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {
        memberService.joinMember(memberCreateDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoDto getMemberInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getId();
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
    public String deleteMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getId();
        memberService.deleteMember(id);
        return "회원 탈퇴가 완료되었습니다.";
    }

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public String updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UpdateNicknameDto updateNicknameDto) {
        Long id = userDetails.getId();
        memberService.updateNickName(id, updateNicknameDto);
        return "닉네임 수정이 완료되었습니다.";
    }
}
