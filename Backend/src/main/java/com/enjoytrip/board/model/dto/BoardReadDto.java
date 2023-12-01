package com.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoardReadDto {

    private final String title;

    private final String content;

    private final LocalDateTime createTime;

    private final String nickname;

    private final boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }
}
