package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardListDto {

    private final int boardId;
    private final String title;
    private final String nickname;
    private final String createTime;
}
