package com.enjoytrip.auth;

import static com.enjoytrip.domain.exception.ExceptionMessage.MEMBER_NOT_FOUND;

import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.comment.repository.CommentRepository;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean authorizeToUpdateBoard(Long boardId, Member member) {
        return boardRepository.findByIdAndMemberAndIsDeletedFalse(boardId, member)
                              .isPresent();
    }

    public boolean authorizeToUpdateComment(Long commentId, Member member) {
        return commentRepository.findByIdAndMemberAndIsDeletedFalse(commentId, member)
                                .isPresent();
    }
}
