package com.enjoytrip.domain.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class BoardLikeId implements Serializable {

    @Column(name="member_id")
    private Long memberId;

    @Column(name="board_id")
    private Long boardId;
}
