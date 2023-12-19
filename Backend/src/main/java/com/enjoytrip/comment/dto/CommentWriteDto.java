package com.enjoytrip.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentWriteDto {

    @NotBlank
    @Size(max = 20)
    private String content;

    @Setter
    private Long boardId;
}
