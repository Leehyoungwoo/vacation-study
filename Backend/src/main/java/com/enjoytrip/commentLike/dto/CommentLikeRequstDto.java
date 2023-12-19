package com.enjoytrip.commentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeRequstDto {

    private Long memberId;

    private Long commentId;
}
