package com.enjoytrip.board.model.entity;

import java.time.LocalDateTime;

public class BoardEntity {

    private Integer boardId;

    private String title;

    private String content;

    private LocalDateTime createTime;

    private Integer memberId;

    private boolean isDeleted;
}
