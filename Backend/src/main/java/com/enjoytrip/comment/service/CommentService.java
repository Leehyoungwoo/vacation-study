package com.enjoytrip.comment.service;

import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;

import java.util.List;

public interface CommentService {
    void writeComment(CommentWriteDto commentWriteDto, Member member);

    void deleteComment(Long commentId);

    void updateComment(UpdateCommentDto updateCommentDto);

    List<Comment> getCommentByBoardId(Long boardId);
}
