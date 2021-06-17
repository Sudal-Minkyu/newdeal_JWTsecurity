package com.broadwave.security.service;

import com.broadwave.security.account.Account;
import com.broadwave.security.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty()) {
            log.info(username+"을 데이터베이스에서 찾을 수 없습니다.");
            return null;
        } else {
            // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(account.get().getAuthority().toString());
            return new User(String.valueOf(account.get().getUsername()), account.get().getPassword(), Collections.singleton(grantedAuthority));
        }
    }

}
