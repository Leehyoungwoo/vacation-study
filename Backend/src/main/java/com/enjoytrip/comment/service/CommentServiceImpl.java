package com.enjoytrip.comment.service;

import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.comment.dto.CommentReadDto;
import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.comment.mapper.CommentMapper;
import com.enjoytrip.comment.repository.CommentRepository;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.exception.CommentNotFoundException;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.enjoytrip.domain.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<CommentReadDto> getCommentByBoardId(Long boardId) {
        boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));
        return commentRepository.findByBoardIdAndIsDeletedFalse(boardId)
                .stream()
                .map(CommentReadDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void writeComment(CommentWriteDto commentWriteDto) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(commentWriteDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));
        Member member = memberRepository.findByIdAndIsDeletedFalse(commentWriteDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        commentRepository.save(CommentMapper.toEntity(commentWriteDto, member, board));
    }

    @Override
    @Transactional
    public void updateComment(UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(updateCommentDto.getId())
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));
        comment.updateComments(updateCommentDto);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));
        comment.markAsDeleted();
    }
}
