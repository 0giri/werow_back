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
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class AuthController {

    private final KakaoService KakaoService;
    private final LoginService loginService;

    @ApiOperation(value = "카카오 로그인", notes = "code로 카카오의 회원 정보를 조회하고, 이 정보와 일치하는 유저가 있다면 로그인 처리, 없다면 카카오 회원 정보 반환")
    @GetMapping(value = "/oauth2/kakao")
    public ResponseEntity<OAuth2Response> kakaoLogin(String code) {
        OAuth2UserInfo kakaoUserInfo = KakaoService.getUserInfo(code);
        OAuth2Response oAuth2Response = loginService.oAuth2Login(kakaoUserInfo);
        return ResponseEntity.ok(oAuth2Response);
    }

    @ApiOperation(value = "이메일 로그인", notes = "로그인 폼에 입력한 정보가 유효하다면 로그인 처리")
    @PostMapping(value = "/login/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> emailLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.emailLogin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "Access 토큰 재발급", notes = "Refresh 토큰을 받아 확인하고, 유효하다면 새로운 Access 토큰 발급")
    @GetMapping("/token/refresh")
    public ResponseEntity<RefreshResponse> getNewAccessToken() {
        return ResponseEntity.ok(loginService.refreshAccessToken());
    }
}
