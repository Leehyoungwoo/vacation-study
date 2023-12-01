package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class BoardWritingDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    @NotNull
    private final Integer memberId;
}
