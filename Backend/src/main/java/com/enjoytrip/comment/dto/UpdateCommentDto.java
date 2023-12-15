package com.enjoytrip.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UpdateCommentDto {

    @Setter
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String content;
}
