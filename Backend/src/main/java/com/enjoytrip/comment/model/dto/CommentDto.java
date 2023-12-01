package com.enjoytrip.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class CommentDto {

    private final Integer commentId;

    @NotNull
    private final Integer boardId;

    @NotBlank
    private final String content;

    private final String create_time;

    @NotBlank
    private final Integer memberId;
}
