package com.enjoytrip.commentLike.repository;

import com.enjoytrip.domain.model.entity.CommentLike;
import com.enjoytrip.domain.model.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
}
