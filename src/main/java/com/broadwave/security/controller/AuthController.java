package com.broadwave.security.controller;

import com.broadwave.security.common.AjaxResponse;
import com.broadwave.security.controller.dto.AccountRequestDto;
import com.broadwave.security.controller.dto.TokenDto;
import com.broadwave.security.controller.dto.TokenRequestDto;
import com.broadwave.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AccountRequestDto accountRequestDto) {
        log.info("accountRequestDto 아이디 : "+accountRequestDto.getUserid());
        log.info("accountRequestDto 비밀번호 : "+passwordEncoder.encode(accountRequestDto.getPassword()));

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        TokenDto tokenDto = authService.login(accountRequestDto);

        data.put("token",tokenDto);

        res.addResponse("data",data);

        return ResponseEntity.ok(res.success());
    }

    // 토큰 재발행
    @PostMapping("/reissue")
    public ResponseEntity<Map<String,Object>> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        log.info("reissue 진입");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        TokenDto tokenDto = authService.reissue(tokenRequestDto);

        data.put("token",tokenDto);
        res.addResponse("data",data);
        if(tokenDto != null){
            return ResponseEntity.ok(res.success());
        }else{
            return ResponseEntity.ok(res.fail());
        }
    }

}
