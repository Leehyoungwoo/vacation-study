package com.enjoytrip.board.dto;


import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardReadDto {

    private final String title;

    private final String content;

    private final Timestamp currentTime;

    private final String author;
}
