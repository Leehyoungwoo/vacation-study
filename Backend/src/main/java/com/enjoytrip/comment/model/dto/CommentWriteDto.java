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
    private Integer boardId;

    @NotBlank
    private String content;

    private String createTime;

    @NotBlank
    private String memberId;
}
