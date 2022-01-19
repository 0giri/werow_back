package com.werow.web.auth.controller;

import com.werow.web.auth.dto.*;
import com.werow.web.auth.service.KakaoService;
import com.werow.web.auth.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final KakaoService KakaoService;
    private final LoginService loginService;

    @ApiOperation(value = "카카오 회원 정보 조회", notes = "카카오에 저장된 회원 정보 조회 (회원 가입 폼으로 이동하기 전 호출)")
    @GetMapping(value = "/auth/kakao")
    public ResponseEntity<OAuth2UserInfo> getKakaoUserInfo(String code) {
        OAuth2UserInfo kakaoUserInfo = KakaoService.getUserInfo(code);
        return ResponseEntity.ok(kakaoUserInfo);
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 회원 정보와 일치하는 유저가 있다면 Access, Refresh 토큰 생성하여 반환")
    @GetMapping(value = "/login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(String code) {
        OAuth2UserInfo kakaoUserInfo = KakaoService.getUserInfo(code);
        LoginResponse loginResponse = loginService.oAuth2Login(kakaoUserInfo);
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "이메일 로그인", notes = "로그인 폼에 입력한 정보가 유효하다면 Access, Refresh 토큰 생성하여 반환")
    @PostMapping(value = "/login/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> emailLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.emailLogin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "Access 토큰 재발급", notes = "전달받은 Refresh 토큰이 유효하다면 새로운 Access 토큰 발급")
    @GetMapping("/auth/refresh")
    public ResponseEntity<RefreshResponse> getNewAccessToken() {
        return ResponseEntity.ok(loginService.refreshAccessToken());
    }
}
