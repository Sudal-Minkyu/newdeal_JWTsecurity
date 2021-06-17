package com.broadwave.security.controller;

import com.broadwave.security.common.AjaxResponse;
import com.broadwave.security.controller.dto.AccountRequestDto;
import com.broadwave.security.controller.dto.AccountResponseDto;
import com.broadwave.security.controller.dto.TokenDto;
import com.broadwave.security.controller.dto.TokenRequestDto;
import com.broadwave.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입
//    @PostMapping("/signup")
//    public ResponseEntity<AccountResponseDto> signup(@RequestBody AccountRequestDto accountRequestDto) {
//        return ResponseEntity.ok(authService.signup(accountRequestDto));
//    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AccountRequestDto accountRequestDto) {
        log.info("accountRequestDto 아이디 : "+accountRequestDto.getUsername());
        log.info("accountRequestDto 비밀번호 : "+accountRequestDto.getPassword());

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        TokenDto tokenDto = authService.login(accountRequestDto);

        data.put("token",tokenDto);
        res.addResponse("data",data);

        return ResponseEntity.ok(res.success());
    }

    // 토큰 재발행
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

}
