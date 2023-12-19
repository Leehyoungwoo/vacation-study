package com.enjoytrip.comment.repository;

import com.enjoytrip.domain.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.id = :id and c.isDeleted=false")
    Optional<Comment> findById(Long id);

    List<Comment> findByBoardIdAndIsDeletedFalse(Long commentId);
}
