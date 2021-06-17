package com.broadwave.security.controller.dto;

import lombok.*;

// 2021/5/7 김민규
// 로그인후, JWT토큰 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
