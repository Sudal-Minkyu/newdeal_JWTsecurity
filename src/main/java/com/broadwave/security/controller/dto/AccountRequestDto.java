package com.broadwave.security.controller.dto;

import com.broadwave.security.account.Account;
import com.broadwave.security.account.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {

    private String userid;
    private String password;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .userid(userid)
                .password(passwordEncoder.encode(password))
                .role(AccountRole.ROLE_ADMIN)
                .insertDateTime(LocalDateTime.now())
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userid, password);
    }
}
