package com.enjoytrip.boardLike.model.service;

public interface BoardLikeService {

    boolean hasUserLikedBoard(int boardId, int memberId);

    void likeBoard(int boardId, int memberId);

    void unlikeBoard(int boardId, int memberId);

    int getLikesCount(int boardId);
}
