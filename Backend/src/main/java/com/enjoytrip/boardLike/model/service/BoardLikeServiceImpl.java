package com.enjoytrip.boardLike.model.service;

import com.enjoytrip.board.model.mapper.BoardMapper;
import com.enjoytrip.boardLike.model.mapper.BoardLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardLikeMapper boardLikeMapper;
    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public boolean hasUserLikedBoard(int boardId, int memberId) {
        return boardLikeMapper.hasUserLikedBoard(boardId, memberId) > 0;
    }

    @Override
    @Transactional
    public void likeBoard(int boardId, int memberId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        boardLikeMapper.likeBoard(boardId, memberId);
    }

    @Override
    @Transactional
    public void unlikeBoard(int boardId, int memberId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        boardLikeMapper.unlikeBoard(boardId, memberId);
    }

    @Override
    @Transactional
    public int getLikesCount(int boardId) {
        if (boardMapper.readBoard(boardId) == null) {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }

        return boardLikeMapper.getLikesCount(boardId);
    }
}
