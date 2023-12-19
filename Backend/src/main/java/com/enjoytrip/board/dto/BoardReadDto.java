package com.enjoytrip.board.dto;

import com.enjoytrip.domain.model.entity.Board;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardReadDto {

    private String title;

    private String content;

    private Timestamp currentTime;

    private String author;

    public BoardReadDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.currentTime = board.getCurrentTime();
        this.author = board.getMember().getName();
    }

}
