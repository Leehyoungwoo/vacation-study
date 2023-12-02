package com.enjoytrip.board.model.service;

import com.enjoytrip.board.model.dto.BoardListDto;
import com.enjoytrip.board.model.dto.BoardReadDto;
import com.enjoytrip.board.model.dto.BoardUpdateDto;
import com.enjoytrip.board.model.dto.BoardWritingDto;

import java.util.List;

public interface BoardService {

    Integer writeBoard(BoardWritingDto boardWritingDto);

    BoardReadDto readBoard(int boardId);

    void updateBoard(int boardId, BoardUpdateDto updatedBoardDto);

    void deleteBoard(int boardId);

    List<BoardListDto> getBoardList(int pageNo, int pageSize);

    List<BoardListDto> searchBoard(String searchType, String keyword, int pageNo, int pageSize);

    int countBoard();

    int countSearchResults(String searchType, String keyword);
}
