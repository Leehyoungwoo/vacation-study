package com.enjoytrip.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentReadDto {

    private Integer commentId;

    private String content;

    private LocalDateTime create_time;

    private String nickname;

}
