package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter

public class BoardUpdateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
