package com.broadwave.security.controller.dto;

import com.broadwave.security.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private String username;

    public static AccountResponseDto of(Account account) {
        return new AccountResponseDto(account.getUsername());
    }
}
