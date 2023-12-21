package com.enjoytrip.domain.model.entity;

import com.enjoytrip.comment.dto.UpdateCommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @NotBlank
    @Size(max = 200)
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="is_deleted")
    private boolean isDeleted;

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void updateComments(UpdateCommentDto updateCommentDto) {
        this.content = updateCommentDto.getContent();
    }

    public boolean isWrittenByTargetMember(Long memberId) {
        return this.member.getId().equals(memberId);
    }
}
