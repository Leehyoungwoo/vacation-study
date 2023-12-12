package com.enjoytrip.board.controller;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.service.BoardService;
import com.enjoytrip.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BoardReadDto> getBoardPage(@RequestParam(defaultValue = "1") int pageNo,
                                           @RequestParam(defaultValue = "5") int offset) {
        return boardService.getBoardPage(pageNo, offset);
    }

    @GetMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardReadDto readBoard(@PathVariable Long boardId) {
        return boardService.readBoard(boardId);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateDto boardUpdateDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        return boardService.updateBoard(boardId, boardUpdateDto, memberId);
    }

    @DeleteMapping("{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        boardService.deleteBoard(boardId, memberId);
        return "글이 삭제되었습니다.";
    }
}
