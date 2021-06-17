//package com.broadwave.security.controller;
//
//import com.broadwave.security.controller.dto.AccountResponseDto;
////import com.broadwave.security.service.AccountService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/account")
//public class AccountController {
//    private final AccountService accountService;
//
//    @GetMapping("/me")
//    public ResponseEntity<AccountResponseDto> getMyAccountInfo() {
//        return ResponseEntity.ok(accountService.getMyInfo());
//    }
//
//    @GetMapping("/{username}")
//    public ResponseEntity<AccountResponseDto> getAccountInfo(@PathVariable String username) {
//        return ResponseEntity.ok(accountService.getAccountInfo(username));
//    }
//}