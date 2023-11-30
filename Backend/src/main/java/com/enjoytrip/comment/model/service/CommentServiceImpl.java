package com.enjoytrip.comment.model.service;

import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.comment.model.dto.CommentDto;
import com.enjoytrip.comment.model.dto.CommentReadDto;
import com.enjoytrip.comment.model.dto.CommentUpdateDto;
import com.enjoytrip.comment.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final BoardService boardService;

    @Override
    public void writeComment(CommentDto writeCommentDto) {
        if (StringUtils.isEmpty(writeCommentDto.getContent())) {
            throw new IllegalArgumentException("댓글을 입력하세요.");
        }

        commentMapper.writeComment(writeCommentDto);
    }

    @Override
    public List<CommentReadDto> getListByBoardId(int boardId) {
        if (!boardService.existsBoard(boardId)) {
            throw new NoSuchElementException("게시물이 존재하지 않습니다.");
        }
        return commentMapper.getCommentList(boardId);
    }

    @Override
    public void updateComment(CommentUpdateDto updateCommentDto) {
        commentMapper.updateComment(updateCommentDto);
    }

    @Override
    public void deleteComment(int commentId) {
        if (commentMapper.getCommentById(commentId) == null) {
            throw new NoSuchElementException("댓글이 존재하지 않습니다.");
        }

        commentMapper.deleteComment(commentId);
    }

    @Override
    public CommentDto getCommentDto(int commentId) {
        CommentDto commentDto = commentMapper.getCommentById(commentId);
        return commentDto;
    }
}
