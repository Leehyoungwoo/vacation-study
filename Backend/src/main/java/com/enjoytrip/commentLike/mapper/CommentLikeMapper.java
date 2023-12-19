package com.enjoytrip.commentLike.mapper;

import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;
import com.enjoytrip.domain.model.entity.CommentLikeId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeMapper {

    public static CommentLikeId toCommentLikeId(CommentLikeRequstDto requstDto) {
        return CommentLikeId.builder()
                .memberId(requstDto.getMemberId())
                .commentId(requstDto.getCommentId())
                .build();
    }
}
