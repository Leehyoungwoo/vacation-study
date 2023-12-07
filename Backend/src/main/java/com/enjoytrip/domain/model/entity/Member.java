package com.enjoytrip.domain.model.entity;

import com.enjoytrip.domain.exception.InvalidEmailFormatException;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.dto.MemberCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30)
    @Column(unique = true)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 10)
    @Column(unique = true)
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    public void validateUsernameFormat() {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (!this.username.matches(emailRegex)) {
            throw new InvalidEmailFormatException("이메일이 올바른 형식이 아닙니다");
        }
    }

    public static Member toEntity(MemberCreateDto memberCreateDto) {
        return Member.builder()
                .username(memberCreateDto.getUsername())
                .password(memberCreateDto.getPassword())
                .name(memberCreateDto.getName())
                .nickname(memberCreateDto.getNickname())
                .role(Role.USER)
                .isDeleted(false)
                .build();
    }
}
