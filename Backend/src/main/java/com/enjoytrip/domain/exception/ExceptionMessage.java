package com.enjoytrip.domain.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS("이미 존재하는 회원입니다."),
    MEMBER_DUPLICATED_NICKNAME("이미 존재하는 별명입니다."),
    BOARD_NOT_FOUND("게시물을 찾을 수 없습니다."),
    TOKEN_NOT_FOUND("토큰을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    BOARDLIKE_NOT_FOUND("좋아요를 찾을 수 없습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
