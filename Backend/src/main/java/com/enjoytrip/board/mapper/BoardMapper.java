package com.enjoytrip.board.mapper;

import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.domain.model.entity.Board;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BoardMapper {

    public static Board toEntity(BoardWriteDto boardWriteDto) {
        return Board.builder()
                .title(boardWriteDto.getTitle())
                .content(boardWriteDto.getContent())
                .id(boardWriteDto.getMemberId())
                .isDeleted(false)
                .build();
    }
}
