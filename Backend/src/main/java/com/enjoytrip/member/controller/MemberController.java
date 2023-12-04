package com.enjoytrip.member.controller;

import com.enjoytrip.member.dto.MemberCreateDto;
import com.enjoytrip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberSerivce;

    @PostMapping("/signup")
    public ResponseEntity<String> joinMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {
        memberSerivce.joinMember(memberCreateDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
