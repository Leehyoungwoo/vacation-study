package com.enjoytrip.board.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
