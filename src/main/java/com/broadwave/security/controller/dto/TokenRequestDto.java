package com.broadwave.security.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String insert_id;
    private String accessToken;
    private String refreshToken;
}
