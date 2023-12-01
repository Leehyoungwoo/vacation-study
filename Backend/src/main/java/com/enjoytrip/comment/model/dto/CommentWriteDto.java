package com.enjoytrip.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class CommentWriteDto {
    private Integer commentId;

    @NotNull
    private final Integer boardId;

    @NotBlank
    private final String content;

    private final String createTime;

    @NotBlank
    private final String memberId;
}
