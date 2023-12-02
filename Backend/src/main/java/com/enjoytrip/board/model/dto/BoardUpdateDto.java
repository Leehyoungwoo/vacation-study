package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter

public class BoardUpdateDto {

    @NotBlank
    @Length(max = 100)
    private final String title;

    @NotBlank
    private final String content;
}
