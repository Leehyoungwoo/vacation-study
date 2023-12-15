package com.enjoytrip.board.dto;

import com.enjoytrip.domain.model.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardReadDto {

    private String title;

    private String content;

    private LocalDateTime currentTime;

    private String author;

    public BoardReadDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.currentTime = board.getCurrentTime().toLocalDateTime();
        this.author = board.getMember().getName();
    }

}
