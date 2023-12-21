package com.enjoytrip.board.dto;

import com.enjoytrip.domain.model.entity.Board;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class BoardReadDto {

    private final String title;

    private final String content;

    private final Timestamp currentTime;

    private final String author;

    public BoardReadDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.currentTime = board.getCurrentTime();
        this.author = board.getMember().getName();
    }

}
