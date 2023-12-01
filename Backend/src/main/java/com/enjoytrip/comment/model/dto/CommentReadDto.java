package com.enjoytrip.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentReadDto {

    private final Integer commentId;

    private final String content;

    private final LocalDateTime create_time;

    private final String nickname;

}
