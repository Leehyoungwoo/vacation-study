package com.enjoytrip.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordUpdateDto {

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String currentPassword;

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String newPassword;
}
