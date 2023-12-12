package com.enjoytrip.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class BoardWriteDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @Setter
    private Long memberId;

    public BoardWriteDto(@NotEmpty String title, @NotEmpty String content) {
        this.title = title;
        this.content = content;
    }
}
