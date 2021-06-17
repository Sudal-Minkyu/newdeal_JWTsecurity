//package com.broadwave.security.service;
//
//import com.broadwave.security.account.AccountRepository;
//import com.broadwave.security.controller.dto.AccountResponseDto;
//import com.broadwave.security.util.SecurityUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AccountService {
//    private final AccountRepository accountRepository;
//
//    @Transactional(readOnly = true)
//    public AccountResponseDto getAccountInfo(String email) {
//        return accountRepository.findByUsername(email)
//                .map(AccountResponseDto::of)
//                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
//    }
//
//    // 현재 SecurityContext 에 있는 유저 정보 가져오기
//    @Transactional(readOnly = true)
//    public AccountResponseDto getMyInfo() {
//        return accountRepository.findById(SecurityUtil.getCurrentAccountName())
//                .map(AccountResponseDto::of)
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//    }
//}
