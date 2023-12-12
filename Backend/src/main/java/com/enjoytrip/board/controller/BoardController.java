package com.enjoytrip.board.controller;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.service.BoardService;
import com.enjoytrip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("write")
    @ResponseStatus(HttpStatus.CREATED)
    public String writeBoard(@Valid @RequestBody BoardWriteDto boardWriteDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardWriteDto.setMemberId(customUserDetails.getId());
        boardService.writeBoard(boardWriteDto);
        return "글이 작성되었습니다.";
    }

    @GetMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardReadDto readBoard(@PathVariable Long boardId) {
        return boardService.readBoard(boardId);
    }
}
