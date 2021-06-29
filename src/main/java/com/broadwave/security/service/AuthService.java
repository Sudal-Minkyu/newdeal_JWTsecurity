package com.broadwave.security.service;

import com.broadwave.security.account.Account;
import com.broadwave.security.account.AccountRepository;
import com.broadwave.security.common.AjaxResponse;
import com.broadwave.security.controller.dto.AccountRequestDto;
import com.broadwave.security.controller.dto.AccountResponseDto;
import com.broadwave.security.controller.dto.TokenDto;
import com.broadwave.security.controller.dto.TokenRequestDto;
import com.broadwave.security.jwt.TokenProvider;
import com.broadwave.security.token.refresh.RefreshToken;
import com.broadwave.security.token.refresh.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// jwt토큰 서비스
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final AccountRepository accountRepository;
//    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

//    회원가입 함수
//    @Transactional
//    public AccountResponseDto signup(AccountRequestDto accountRequestDto) {
//        if (accountRepository.existsByUsername(accountRequestDto.getUsername())) {
//            throw new RuntimeException("이미 가입되어 있는 유저입니다");
//        }
//
//        Account account = accountRequestDto.toAccount(passwordEncoder);
//        return AccountResponseDto.of(accountRepository.save(account));
//    }

    @Transactional
    public TokenDto login(AccountRequestDto accountRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = accountRequestDto.toAuthentication();

        // 2. 실제로 검증이 이루어지는 부분 -> 사용자 아이디,비밀번호 체크
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("사용자 검증 : "+authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        log.info("인증 정보를 기반으로 JWT_AccessToken 생성 : "+tokenDto.getAccessToken());

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userid(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        log.info("RefreshToken DB저장 : "+refreshToken.getValue());

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    // 토큰 리플래쉬 재발행 함수
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {


//        // 3. 받은 RefreshToken 과 해당유저 DB의 RefreshToken이 일치하는지 확인한다.
//        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserid(tokenRequestDto.getInsert_id());
//        if(optionalRefreshToken.isPresent()){
//            // 4. Refresh Token 일치하는지 검사
//            if(optionalRefreshToken.get().getValue().equals(tokenRequestDto.getRefreshToken())){
//
//                UsernamePasswordAuthenticationToken authenticationToken = accountRequestDto.toAuthentication();
//                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//                // 5. 새로운 토큰 생성
//                TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//                RefreshToken refreshToken = refreshTokenRepository.findByUserid(authentication.getName())
//                        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
//
//                // 6. 저장소 정보 업데이트
//                RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//                refreshTokenRepository.save(newRefreshToken);
//
//                // 7. 토큰 발급
//                return tokenDto;
//
//            }else{
//                throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//            }
//        }else{
//            throw new RuntimeException("DB정보가 존재하지 않습니다.");
//        }

        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }else{
            log.info("유효한 Refresh Token");
        }

        log.info("tokenRequestDto.getInsert_id 테스트 : "+tokenRequestDto.getInsert_id());
        log.info("tokenRequestDto.getAccessToken 테스트 : "+tokenRequestDto.getAccessToken());
        log.info("tokenRequestDto.getRefreshToken 테스트 : "+tokenRequestDto.getRefreshToken());

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        log.info("authentication 테스트 : "+authentication.getName());

        // 3. 저장소에서 Account ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByUserid(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

}
