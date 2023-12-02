package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class BoardWritingDto {

    @NotBlank
    @Length(max = 100)
    private final String title;

    @NotBlank
    private final String content;

    @NotNull
    private final Integer memberId;
}
