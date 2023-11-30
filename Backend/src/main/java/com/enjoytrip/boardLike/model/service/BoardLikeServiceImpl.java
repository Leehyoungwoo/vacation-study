package com.enjoytrip.boardLike.model.service;

import com.enjoytrip.board.model.mapper.BoardMapper;
import com.enjoytrip.boardLike.model.mapper.BoardLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardLikeMapper boardLikeMapper;
    private final BoardMapper boardMapper;

    @Override
    public boolean hasUserLikedBoard(int boardId, int memberId) {
        return boardLikeMapper.hasUserLikedBoard(boardId, memberId) > 0;
    }

    @Override
    public void likeBoard(int boardId, int memberId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        boardLikeMapper.likeBoard(boardId, memberId);
    }

    @Override
    public void unlikeBoard(int boardId, int memberId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        boardLikeMapper.unlikeBoard(boardId, memberId);
    }

    @Override
    public int getLikesCount(int boardId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        return boardLikeMapper.getLikesCount(boardId);
    }
}
