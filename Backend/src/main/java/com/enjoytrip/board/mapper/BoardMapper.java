package com.enjoytrip.board.mapper;

import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BoardMapper {

    public static Board toEntity(BoardWriteDto boardWriteDto, Member member) {
        return Board.builder()
                .title(boardWriteDto.getTitle())
                .content(boardWriteDto.getContent())
                .member(member)
                .isDeleted(false)
                .build();
    }
}
