package com.enjoytrip.commentLike.controller;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;
import com.enjoytrip.commentLike.service.CommentLikeService;
import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{boardId}/{commentId}")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @GetMapping("/likes")
    @ResponseStatus(HttpStatus.OK)
    public Integer getLikeCount(@PathVariable Long commentId) {
        return commentLikeService.getLikeCount(commentId);
    }

    @GetMapping("/likeStatus")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkLikeStatus(@PathVariable Long commentId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        CommentLikeRequstDto requstDto = CommentLikeRequstDto.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
        return commentLikeService.checkLikeStatus(requstDto);
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authService.authorizeToLikeComment(#userDetails.getId(), #commentId)")
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
    @PreAuthorize("authService.authorizeToLikeComment(#userDetails.getId(), #commentId)")
    public void unlikeComment(@PathVariable Long commentId, @AuthenticationPrincipal Member userDetails) {
        Long memberId = userDetails.getId();
        CommentLikeRequstDto requstDto = CommentLikeRequstDto.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
        commentLikeService.unlikeComment(requstDto);
    }
}
