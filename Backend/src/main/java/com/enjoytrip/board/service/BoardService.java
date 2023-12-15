package com.enjoytrip.board.service;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Long writeBoard(BoardWriteDto boardWriteDto);

    BoardReadDto readBoard(Long boardId);

    Page<BoardReadDto> getBoardPage(Pageable pageable);

    String updateBoard(Long boardId, BoardUpdateDto boardUpdateDto, Long memberId);

    void deleteBoard(Long boardId, Long memberId);
}
