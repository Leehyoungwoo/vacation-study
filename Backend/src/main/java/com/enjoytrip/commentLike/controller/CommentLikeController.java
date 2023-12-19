package com.enjoytrip.commentLike.controller;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;
import com.enjoytrip.commentLike.service.CommentLikeService;
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
