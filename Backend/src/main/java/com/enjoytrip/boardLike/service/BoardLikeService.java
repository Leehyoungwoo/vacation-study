package com.enjoytrip.boardLike.service;

import com.enjoytrip.boardLike.dto.BoardLikeRequestDto;
import com.enjoytrip.domain.model.entity.BoardLikeId;

public interface BoardLikeService {

    Integer getLikeCount(Long boardId);

    boolean checkLikeStatus(BoardLikeRequestDto requestDto);

    void likeBoard(BoardLikeRequestDto requestDto);

    void unlikeBoard(BoardLikeRequestDto requestDto);

}
