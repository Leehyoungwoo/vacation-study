package com.enjoytrip.board.controller;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.service.BoardService;
import com.enjoytrip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Long writeBoard(@Valid @RequestBody BoardWriteDto boardWriteDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boardWriteDto.setMemberId(customUserDetails.getId());
        return boardService.writeBoard(boardWriteDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardReadDto> getBoardPage(@PageableDefault(size = 20) Pageable pageable) {
        return boardService.getBoardPage(pageable);
    }

    @GetMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardReadDto readBoard(@PathVariable Long boardId) {
        return boardService.readBoard(boardId);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authorizeToUpdateBoard(#userDetails.getId(), #boardId)")
    public String updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateDto boardUpdateDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        return boardService.updateBoard(boardId, boardUpdateDto, memberId);
    }

    @DeleteMapping("{boardId}")
    @PreAuthorize("@authService.authorizeToUpdateBoard(#userDetails.getId(), #boardId)")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        boardService.deleteBoard(boardId, memberId);
        return "글이 삭제되었습니다.";
    }
}
