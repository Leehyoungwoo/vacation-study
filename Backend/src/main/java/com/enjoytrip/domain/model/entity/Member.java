package com.enjoytrip.domain.model.entity;

import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.dto.MemberCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 6, max = 20)
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    @Size(max = 20)
    private String name;

    @NotEmpty
    @Size(max = 10)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    public void validateUsernameFormat() {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (!this.username.matches(emailRegex)) {
            throw new IllegalArgumentException("이메일이 올바른 형식이 아닙니다");
        }
    }

    public static Member toEntity(MemberCreateDto memberCreateDto) {
        return Member.builder()
                .username(memberCreateDto.getUsername())
                .password(memberCreateDto.getPassword())
                .name(memberCreateDto.getName())
                .nickname(memberCreateDto.getNickname())
                .role(Role.User)
                .isDeleted(false)
                .build();
    }
}
