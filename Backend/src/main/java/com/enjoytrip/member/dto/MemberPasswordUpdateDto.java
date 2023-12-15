package com.enjoytrip.member.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
