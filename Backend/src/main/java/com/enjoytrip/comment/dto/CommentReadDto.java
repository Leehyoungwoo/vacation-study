package com.enjoytrip.comment.dto;

import com.enjoytrip.domain.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentReadDto {

    private String nickname;

    private String content;

    private Timestamp currentTime;

    public CommentReadDto(Comment comment) {
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.currentTime = comment.getCreatedAt();
    }
}
