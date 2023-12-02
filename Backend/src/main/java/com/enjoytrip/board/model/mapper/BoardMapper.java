package com.enjoytrip.board.model.mapper;

import com.enjoytrip.board.model.dto.BoardListDto;
import com.enjoytrip.board.model.dto.BoardReadDto;
import com.enjoytrip.board.model.dto.BoardUpdateDto;
import com.enjoytrip.board.model.dto.BoardWritingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    int writeBoard(BoardWritingDto boardWritingDto);

    Optional<BoardReadDto> readBoard(int boardId);

    void updateBoard(@Param("boardId") int boardId, @Param("boardUpdateDto") BoardUpdateDto boardUpdateDto);

    void deleteBoard(int boardId);

    List<BoardListDto> getBoardList(int pageSize, int offset);

    List<BoardListDto> searchBoard(String searchType, String keyword, int pageSize, int offset);

    void deleteAllCommentInBoard(int boardId);

    int countBoard();

    int countSearchResults(String searchType, String keyword);
}
