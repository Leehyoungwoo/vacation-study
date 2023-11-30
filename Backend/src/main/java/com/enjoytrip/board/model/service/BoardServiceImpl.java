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

        if (!existsBoard(boardId)) {
            throw new NoSuchElementException("게시물을 찾을 수 없습니다.");
        }

        if (boardReadDto.isDeleted()) {
            throw new IllegalStateException("삭제된 게시물은 수정할 수 없습니다.");
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
        if (!existsBoard(boardId)) {
            throw new NoSuchElementException("게시물을 찾을 수 없습니다.");
        }
//        boardMapper.deleteAllCommentInBoard(boardId);
        boardMapper.deleteBoard(boardId);
    }

    @Override
    public List<BoardListDto> getBoardList(int pageNo, int pageSize) {
        int totalCount = boardMapper.countBoard();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        if (pageNo <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("유효하지 않은 페이지 번호 또는 페이지 크기입니다.");
        }

        if (pageNo > totalPages) {
            throw new NoSuchElementException("요청한 페이지가 존재하지 않습니다.");
        }

        int offset = (pageNo - 1) * pageSize;
        return boardMapper.getBoardList(pageSize, offset);
    }

    @Override
    public List<BoardListDto> searchBoard(String searchType, String keyword, int pageNo, int pageSize) {
        int searchCount = boardMapper.countSearchResults(searchType, keyword);
        int searchPages = (int) Math.ceil((double) searchCount / pageSize);

        if (StringUtils.isEmpty(searchType)) {
            throw new IllegalArgumentException("검색 구분을 선택하세요.");
        }

        if (StringUtils.isEmpty(keyword)) {
            throw new IllegalArgumentException("검색어를 입력하세요.");
        }

        if (pageNo <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("유효하지 않은 페이지 번호 또는 페이지 크기입니다.");
        }

        if (pageNo > searchPages) {
            throw new NoSuchElementException("요청한 페이지가 존재하지 않습니다.");
        }

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
