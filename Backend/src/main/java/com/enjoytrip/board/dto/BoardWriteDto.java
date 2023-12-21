package com.enjoytrip.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteDto {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String content;

    @Setter
    private Long memberId;
}
