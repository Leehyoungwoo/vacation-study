package com.enjoytrip.board.controller;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.service.BoardService;
import javax.validation.Valid;

import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardReadDto readBoard(@PathVariable Long boardId) {
        return boardService.readBoard(boardId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardReadDto> getBoardPage(@PageableDefault(size = 20) Pageable pageable) {
        return boardService.getBoardPage(pageable);
    }

    @PostMapping("write")
    @ResponseStatus(HttpStatus.CREATED)
    public Long writeBoard(@Valid @RequestBody BoardWriteDto boardWriteDto,
                           @AuthenticationPrincipal Member member) {
        boardWriteDto.setMemberId(member.getId());
        return boardService.writeBoard(boardWriteDto);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authorizeToUpdateBoard(#userDetails.getId(), #boardId)")
    public void updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateDto boardUpdateDto,
                              @AuthenticationPrincipal Member userDetails) {
        boardService.updateBoard(boardId, boardUpdateDto);
    }

    @DeleteMapping("{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authorizeToUpdateBoard(#userDetails.getId(), #boardId)")
    public void deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal Member userDetails) {
        boardService.deleteBoard(boardId);
    }
}
