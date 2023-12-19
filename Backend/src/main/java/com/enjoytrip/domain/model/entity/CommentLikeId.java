package com.enjoytrip.domain.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class CommentLikeId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "comment_id")
    private Long commentId;
}
