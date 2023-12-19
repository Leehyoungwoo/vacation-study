package com.enjoytrip.domain.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
