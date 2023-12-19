package com.enjoytrip.comment.mapper;

import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static Comment toEntity(CommentWriteDto commentWriteDto, Member member, Board board) {
        return Comment.builder()
                      .content(commentWriteDto.getContent())
                      .isDeleted(false)
                      .member(member)
                      .board(board)
                      .build();
    }
}
