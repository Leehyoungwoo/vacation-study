package com.enjoytrip.domain.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_likes")

public class BoardLike {

    @EmbeddedId
    private BoardLikeId boardLikeId;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "is_likeed")
    private boolean isLiked;

    public void like() {
        this.isLiked = true;
    }

    public void unlike() {
        this.isLiked = false;
    }
}
