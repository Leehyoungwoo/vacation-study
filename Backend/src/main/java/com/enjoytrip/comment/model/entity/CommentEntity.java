package com.enjoytrip.comment.model.entity;

import java.time.LocalDateTime;

public class CommentEntity {

    private Integer commentId;

    private String content;

    private LocalDateTime createTime;

    private Integer boardId;

    private Integer memberId;

    private boolean isDeleted;
}
