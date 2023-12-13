package com.enjoytrip.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameDto {

    @NotNull
    @NotEmpty
    @Size(max = 10)
    private String newNickname;
}
