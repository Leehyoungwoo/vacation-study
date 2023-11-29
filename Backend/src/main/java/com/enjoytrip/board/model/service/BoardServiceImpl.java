package com.enjoytrip.board.model.service;

import com.enjoytrip.board.model.dto.BoardListDto;
import com.enjoytrip.board.model.dto.BoardReadDto;
import com.enjoytrip.board.model.dto.BoardUpdateDto;
import com.enjoytrip.board.model.dto.BoardWritingDto;
import com.enjoytrip.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public int writeBoard(BoardWritingDto boardWritingDto) {
        if (StringUtils.isEmpty(boardWritingDto.getTitle())) {
            throw new IllegalArgumentException("제목을 입력해주세요");
        }

        if (StringUtils.isEmpty(boardWritingDto.getContent())) {
            throw new IllegalArgumentException("내용을 입력해주세요");
        }
        int boardId = boardMapper.writeBoard(boardWritingDto);

        return boardId;
    }


    @Override
    public BoardReadDto readBoard(int boardId) {
        BoardReadDto boardReadDto = boardMapper.readBoard(boardId);

        if (boardReadDto == null) {
            throw new NoSuchElementException("게시물을 찾을 수 없습니다.");
        }

        return boardReadDto;
    }

    @Override
    public void updateBoard(int boardId, BoardUpdateDto updatedBoardDto) {
        BoardReadDto boardReadDto = boardMapper.readBoard(boardId);

        if (boardReadDto == null) {
            throw new NoSuchElementException("게시물을 찾을 수 없습니다.");
        }

        if (StringUtils.isEmpty(updatedBoardDto.getTitle())) {
            throw new IllegalArgumentException("제목을 입력해주세요");
        }

        if (StringUtils.isEmpty(updatedBoardDto.getContent())) {
            throw new IllegalArgumentException("내용을 입력해주세요");
        }
        boardMapper.updateBoard(boardId, updatedBoardDto);
    }

    @Override
    public void deleteBoard(int boardId) {
        BoardReadDto boardReadDto = boardMapper.readBoard(boardId);

        if (boardReadDto == null) {
            throw new RuntimeException();
        }
        boardMapper.deleteAllCommentInBoard(boardId);
        boardMapper.deleteBoard(boardId);
    }

    @Override
    public List<BoardListDto> getBoardList(int pageNo, int pageSize) {
        int offset = (pageNo - 1) * pageSize;
        return boardMapper.getBoardList(pageSize, offset);
    }

    @Override
    public List<BoardListDto> searchBoard(String searchType, String keyword, int pageNo, int pageSize) {
        int offset = (pageNo - 1) * pageSize;
        return boardMapper.searchBoard(searchType, keyword, pageSize, offset);
    }

    @Override
    public boolean existsBoard(int boardId) {
        return boardMapper.readBoard(boardId) != null;
    }

    @Override
    public int countBoard() {
        return boardMapper.countBoard();
    }

    @Override
    public int countSearchResults(String searchType, String keyword) {
        return boardMapper.countSearchResults(searchType, keyword);
    }
}
