package com.enjoytrip.commentLike.service;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;

public interface CommentLikeService {

    boolean checkLikeStatus(CommentLikeRequstDto requstDto);

    Integer getLikeCount(Long commentId);

    void likeComment(CommentLikeRequstDto requestDto);

    void unlikeComment(CommentLikeRequstDto requstDto);
}
