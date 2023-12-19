package com.enjoytrip.boardLike.mapper;

import com.enjoytrip.boardLike.dto.BoardLikeRequestDto;
import com.enjoytrip.domain.model.entity.BoardLikeId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardLikeMapper {

    public static BoardLikeId toBoardLikeId(BoardLikeRequestDto requestDto) {
        return BoardLikeId.builder()
                          .memberId(requestDto.getMemberId())
                          .boardId(requestDto.getBoardId())
                          .build();
    }
}
