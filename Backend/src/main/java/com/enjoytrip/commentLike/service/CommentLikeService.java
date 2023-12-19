package com.enjoytrip.commentLike.service;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;

public interface CommentLikeService {
    void likeComment(CommentLikeRequstDto requestDto);

    void unlikeComment(CommentLikeRequstDto requstDto);
}
