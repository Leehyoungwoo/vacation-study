package com.enjoytrip.comment.service;

import com.enjoytrip.comment.dto.CommentReadDto;
import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;

import java.util.List;

public interface CommentService {
    List<CommentReadDto> getCommentByBoardId(Long boardId);

    void writeComment(CommentWriteDto commentWriteDto);

    void deleteComment(Long commentId);

    void updateComment(UpdateCommentDto updateCommentDto);
}
