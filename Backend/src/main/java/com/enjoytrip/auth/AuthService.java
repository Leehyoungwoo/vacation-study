package com.enjoytrip.auth;

import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.comment.repository.CommentRepository;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.exception.CommentNotFoundException;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.enjoytrip.domain.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public boolean authenticatePassword(final Long id, final String password) {
        final Member member = memberRepository.findByIdAndIsDeletedFalse(id)
                                              .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return passwordEncoder.matches(password, member.getPassword());
    }

    public boolean authorizeToUpdateBoard(Long memberId, Long boardId) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));

        return board.isWrittenByTargetMember(memberId) || isAdminRole(memberId);
    }

    public boolean authorizeToUpdateComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        return comment.isWrittenByTargetMember(memberId) || isAdminRole(memberId);
    }

    public boolean authorizeToLikeBoard(Long memberId, Long boardId) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));

        return !board.isWrittenByTargetMember(memberId);
    }

    public boolean authorizeToLikeComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        return !comment.isWrittenByTargetMember(memberId);
    }

    private boolean isAdminRole(Long memberId) {

        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return member.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
