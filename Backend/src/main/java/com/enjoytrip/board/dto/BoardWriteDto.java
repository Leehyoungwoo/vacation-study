package com.enjoytrip.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class BoardWriteDto {

    @NotNull
    @NotEmpty
    private final String title;

    @NotNull
    @NotEmpty
    private final String content;

    @NotNull
    @NotEmpty
    private final Long memberId;
}
