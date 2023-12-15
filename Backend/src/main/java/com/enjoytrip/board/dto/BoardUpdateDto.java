package com.enjoytrip.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BoardUpdateDto {

    @NotEmpty
    @NotNull
    private final String title;

    @NotEmpty
    @NotNull
    private final String content;


}
