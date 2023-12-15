package com.enjoytrip.board.repository;

import com.enjoytrip.domain.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.id = :id AND b.isDeleted = false")
    Optional<Board> findById(@Param("id") Long id);

    Page<Board> findByIsDeletedFalse(Pageable pageable);

//    @Modifying
//    @Query("UPDATE Board b SET b.title = :title, b.content = :content WHERE b.id = :boardId")
//    int updateById(@Param("boardId") Long boardId, @Param("title") String title, @Param("content") String content);

//    @Modifying
//    @Query("UPDATE Board b SET b.isDeleted = true WHERE b.id = :boardId")
//    void markAsDeleted(@Param("boardId") Long boardId);
}
