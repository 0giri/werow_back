package com.werow.web.auth.controller;

import com.werow.web.auth.jwt.JwtUtils;
import com.werow.web.account.entity.User;
import com.werow.web.auth.dto.OAuth2UserInfo;
import com.werow.web.auth.service.KakaoService;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserRepository userRepository;
    private final MemberService memberService;
    private final KakaoService KakaoService;
    private final JwtUtils jwtUtils;


    /**
     * 프론트에서 넘겨준 auth code로 토큰 발급, 토큰으로 회원 정보 가져오기
     */
    @GetMapping("/kakao")
    public ResponseEntity<OAuth2UserInfo> getKakaoUserInfo(String code) {
        OAuth2UserInfo kakaoUserInfo = KakaoService.getUserInfo(code);
        Optional<User> findUser = userRepository.findByEmail(kakaoUserInfo.getEmail());


        if (findUser.isPresent()) { // 회원인 경우

        }
//        objectNode.putPOJO("oauth_user", kakaoUserInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(kakaoUserInfo, headers, HttpStatus.OK);
    }

//    @GetMapping("/email")
//    public
}
