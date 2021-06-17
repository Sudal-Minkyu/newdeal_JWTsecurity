package com.broadwave.security.config;

import com.broadwave.security.account.Account;
import com.broadwave.security.account.AccountRepository;
import com.broadwave.security.account.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 최초실행시 테스트할 계정임시로 만들기
    @Override
    public void run(ApplicationArguments args) {
        Account account = Account.builder()
                .username("admin")
                .password(passwordEncoder.encode("123789"))
                .authority(Authority.ROLE_ADMIN)
                .insertDate(String.valueOf(LocalDate.now()))
                .build();

        Optional<Account> accountCheck = accountRepository.findByUsername(account.getUsername());
        if(accountCheck.isPresent()){
            log.info("admin 계정존재");
        }else{
            accountRepository.save(account);
        }
    }

}
