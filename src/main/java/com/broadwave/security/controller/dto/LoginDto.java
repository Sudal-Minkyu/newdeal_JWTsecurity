package com.broadwave.security.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// 2021/5/7 김민규
// 로그인관련 DTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private  String username;

    @NotNull
    @Size(min = 3,max = 100)
    private  String password;

}
