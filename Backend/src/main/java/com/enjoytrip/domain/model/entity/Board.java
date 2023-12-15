package com.enjoytrip.domain.model.entity;

import com.enjoytrip.board.dto.BoardUpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="boards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max=50)
    private String title;

    @NotNull
    @NotEmpty
    @Size(max=1000)
    private String content;

    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp currentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    public void delete() {
        this.isDeleted = true;
    }

    public void update(BoardUpdateDto boardUpdateDto) {
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
    }
}
