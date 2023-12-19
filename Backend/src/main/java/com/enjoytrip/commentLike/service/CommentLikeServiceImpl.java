package com.enjoytrip.commentLike.service;

import com.enjoytrip.comment.repository.CommentRepository;
import com.enjoytrip.commentLike.dto.CommentLikeRequstDto;
import com.enjoytrip.commentLike.repository.CommentLikeRepository;
import com.enjoytrip.domain.exception.CommentLikeNotFoundException;
import com.enjoytrip.domain.exception.CommentNotFoundException;
import com.enjoytrip.domain.exception.ExceptionMessage;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.CommentLike;
import com.enjoytrip.domain.model.entity.CommentLikeId;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.enjoytrip.domain.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeServiceImpl implements CommentLikeService{

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public Integer getLikeCount(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));
        return commentLikeRepository.countByCommentId(commentId);
    }

    @Override
    @Transactional
    public void likeComment(CommentLikeRequstDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        CommentLikeId commentLikeId = CommentLikeId.builder()
                .memberId(requestDto.getMemberId())
                .commentId(requestDto.getCommentId())
                .build();
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
                .orElseGet(() ->commentLikeRepository.save(
                        CommentLike.builder()
                                .commentLikeId(commentLikeId)
                                .member(member)
                                .comment(comment)
                                .isLiked(false)
                                .build()
                ));

        commentLike.like();
    }

    @Override
    @Transactional
    public void unlikeComment(CommentLikeRequstDto requstDto) {
        CommentLikeId commentLikeId = CommentLikeId.builder()
                .memberId(requstDto.getMemberId())
                .commentId(requstDto.getCommentId())
                .build();

        CommentLike commentLike = commentLikeRepository.findById(commentLikeId).
                orElseThrow(() -> new CommentLikeNotFoundException(COMMENTLIKE_NOT_FOUND));

        commentLike.unlike();
    }
}
