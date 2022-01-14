package com.werow.web.auth.controller;

import com.werow.web.auth.dto.AuthResponse;
import com.werow.web.auth.dto.EmailLoginRequest;
import com.werow.web.auth.dto.OAuth2UserInfo;
import com.werow.web.auth.service.KakaoService;
import com.werow.web.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final KakaoService KakaoService;
    private final LoginService loginService;

    @GetMapping("/kakao")
    public ResponseEntity<AuthResponse> kakaoLogin(String code) {
        OAuth2UserInfo kakaoUserInfo = KakaoService.getUserInfo(code);
        AuthResponse authResponse = loginService.oAuth2Login(kakaoUserInfo);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/email")
    public ResponseEntity<AuthResponse> emailLogin(EmailLoginRequest emailLoginRequest) {
        AuthResponse authResponse = loginService.emailLogin(emailLoginRequest);
        return ResponseEntity.ok(authResponse);
    }
}
