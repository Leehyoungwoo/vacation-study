package com.enjoytrip.boardLike.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeMapper {
    int hasUserLikedBoard(int boardId, int memberId);

    void likeBoard(int boardId, int memberId);

    void unlikeBoard(int boardId, int memberId);

    int getLikesCount(int boardId);

}
