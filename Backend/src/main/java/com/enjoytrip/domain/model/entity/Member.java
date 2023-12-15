package com.enjoytrip.domain.model.entity;

import com.enjoytrip.domain.exception.InvalidEmailFormatException;
import com.enjoytrip.domain.model.entity.converter.PasswordConverter;
import com.enjoytrip.domain.model.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Member implements UserDetails {

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
    @Convert(converter = PasswordConverter.class)
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
        if (!EmailValidator.getInstance().isValid(this.username)) {
            throw new InvalidEmailFormatException("이메일이 올바른 형식이 아닙니다.");
        }
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
