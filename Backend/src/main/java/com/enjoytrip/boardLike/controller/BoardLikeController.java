package com.enjoytrip.boardLike.controller;

import com.enjoytrip.boardLike.dto.BoardLikeRequestDto;
import com.enjoytrip.boardLike.service.BoardLikeService;
import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/{boardId}")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @GetMapping("/likes")
    @ResponseStatus(HttpStatus.OK)
    public Integer getLikeCount(@PathVariable Long boardId) {
        return boardLikeService.getLikeCount(boardId);
    }

    @GetMapping("/likeStatus")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkLikeStatus(@PathVariable Long boardId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        BoardLikeRequestDto requestDto = BoardLikeRequestDto.builder()
                                                            .memberId(memberId)
                                                            .boardId(boardId)
                                                            .build();
        return boardLikeService.checkLikeStatus(requestDto);
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeBoard(@PathVariable Long boardId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        BoardLikeRequestDto requestDto = BoardLikeRequestDto.builder()
                                                            .memberId(memberId)
                                                            .boardId(boardId)
                                                            .build();
        boardLikeService.likeBoard(requestDto);
    }

    @DeleteMapping("/unlike")
    @ResponseStatus(HttpStatus.OK)
    public void unlikeBaord(@PathVariable Long boardId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        BoardLikeRequestDto requestDto = BoardLikeRequestDto.builder()
                                                            .memberId(memberId)
                                                            .boardId(boardId)
                                                            .build();
        boardLikeService.unlikeBoard(requestDto);
    }
}
