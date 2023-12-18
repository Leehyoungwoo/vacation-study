package com.enjoytrip.comment.service;

import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.comment.mapper.CommentMapper;
import com.enjoytrip.comment.repository.CommentRepository;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.exception.CommentNotFoundException;
import com.enjoytrip.domain.exception.ExceptionMessage;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.enjoytrip.domain.exception.ExceptionMessage.BOARD_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<Comment> getCommentByBoardId(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));
        return commentRepository.findByBoardIdAndIsDeletedFalse(boardId);
    }

    @Override
    @Transactional
    public void writeComment(CommentWriteDto commentWriteDto, Member member) {
        Board board = boardRepository.findById(commentWriteDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));
        commentRepository.save(CommentMapper.toEntity(commentWriteDto, member, board));
    }

    @Override
    @Transactional
    public void updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findById(updateCommentDto.getId())
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));
        comment.updateComments(updateCommentDto);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));
        comment.markAsDeleted();
    }
}
