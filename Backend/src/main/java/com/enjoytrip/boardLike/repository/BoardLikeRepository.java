package com.enjoytrip.boardLike.repository;

import com.enjoytrip.domain.model.entity.BoardLike;
import com.enjoytrip.domain.model.entity.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {
    Integer countByBoardId(Long boardId);
}
