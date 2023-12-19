package com.enjoytrip.commentLike.controller;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;
import com.enjoytrip.commentLike.service.CommentLikeService;
import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{boardId}/{commentId}")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeComment(@PathVariable Long commentId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        CommentLikeRequstDto requstDto = CommentLikeRequstDto.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
        commentLikeService.likeComment(requstDto);
    }

    @DeleteMapping("/unlike")
    @ResponseStatus(HttpStatus.OK)
    public void unlikeComment(@PathVariable Long commentId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        CommentLikeRequstDto requstDto = CommentLikeRequstDto.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
        commentLikeService.unlikeComment(requstDto);
    }
}
