package com.enjoytrip.board.service;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardWriteDto;

import java.util.List;

public interface BoardService {
    void writeBoard(BoardWriteDto boardWriteDto);

    BoardReadDto readBoard(Long boardId);

    List<BoardReadDto> getBoardPage(int pageNo, int offset);
}
