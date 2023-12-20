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
import org.springframework.security.authentication.BadCredentialsException;
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
        final Member member = memberRepository.findById(id)
                                              .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return passwordEncoder.matches(password, member.getPassword());
    }

    public boolean authorizeToUpdateBoard(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));

        boolean hasAdminRole = isAdminRole(memberId);

        return board.getMember().getId().equals(memberId) || hasAdminRole;
    }

    public boolean authorizeToUpdateComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        return comment.getMember().getId().equals(userId) || isAdminRole(userId);
    }

    private boolean isAdminRole(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        return member.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

}
