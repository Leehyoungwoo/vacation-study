package com.enjoytrip.comment.model.service;

import com.enjoytrip.comment.model.dto.CommentDto;
import com.enjoytrip.comment.model.dto.CommentReadDto;
import com.enjoytrip.comment.model.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    void writeComment(CommentDto writeCommentDto);

    List<CommentReadDto> getListByBoardId(int boardId);

    void updateComment(CommentUpdateDto updateCommentDto);

    void deleteComment(int commentId);

    CommentDto getCommentDto(int commentId);

}
