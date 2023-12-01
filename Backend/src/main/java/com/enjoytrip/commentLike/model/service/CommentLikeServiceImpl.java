package com.enjoytrip.commentLike.model.service;

import com.enjoytrip.comment.model.dto.CommentDto;
import com.enjoytrip.comment.model.service.CommentService;
import com.enjoytrip.commentLike.model.dto.CommentLikeDto;
import com.enjoytrip.commentLike.model.mapper.CommentLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeMapper commentLikeMapper;
    private final CommentService commentService;

    @Override
    @Transactional
    public boolean hasUserLikeComment(CommentLikeDto commentLikeDto) {
        return commentLikeMapper.hasUserLikeComment(commentLikeDto) > 0;
    }

    @Override
    @Transactional
    public void likeComment(CommentLikeDto commentLikeDto) {
        CommentDto commentDto = commentService.getCommentDto(commentLikeDto.getCommentId());
        if (commentDto == null) {
            throw new NoSuchElementException("댓글이 존재하지 않습니다.");
        }

        if (commentDto.getMemberId() == commentLikeDto.getMemberId()) {
            throw new UnsupportedOperationException("댓글을 추천할 수 없습니다.");
        }
        commentLikeMapper.likeComment(commentLikeDto);
    }

    @Override
    @Transactional
    public void unlikeComment(CommentLikeDto commentLikeDto) {
        CommentDto commentDto = commentService.getCommentDto(commentLikeDto.getCommentId());
        if (commentDto == null) {
            throw new NoSuchElementException("댓글이 존재하지 않습니다.");
        }

        if (commentDto.getMemberId() == commentLikeDto.getMemberId()) {
            throw new UnsupportedOperationException("댓글을 추천할 수 없습니다.");
        }

        commentLikeMapper.unlikeComment(commentLikeDto);
    }

    @Override
    @Transactional
    public int getLikesCount(int commentId) {
        CommentDto commentDto = commentService.getCommentDto(commentId);
        if (commentDto == null) {
            throw new NoSuchElementException("댓글이 존재하지 않습니다.");
        }

        return commentLikeMapper.getLikesCount(commentId);
    }
}
