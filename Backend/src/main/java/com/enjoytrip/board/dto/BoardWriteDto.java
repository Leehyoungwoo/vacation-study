package com.enjoytrip.board.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
