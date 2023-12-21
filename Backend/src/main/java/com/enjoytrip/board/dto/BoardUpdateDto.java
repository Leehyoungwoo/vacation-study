package com.enjoytrip.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateDto {

    @NotBlank
    @Size(max = 50)
    private final String title;

    @NotBlank
    @Size(max = 1000)
    private final String content;
}
