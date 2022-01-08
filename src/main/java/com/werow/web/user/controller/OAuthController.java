package com.werow.web.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.werow.web.commons.HttpUtils;
import com.werow.web.user.domain.User;
import com.werow.web.user.dto.OAuthUserInfo;
import com.werow.web.user.provider.kakao.KakaoOAuth2;
import com.werow.web.user.repository.UserRepository;
import com.werow.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final HttpUtils httpUtils;

    @GetMapping("/kakao")
    public ObjectNode getKakaoUser(String code) {
        OAuthUserInfo kakaoUserInfo = kakaoOAuth2.getKakaoUserInfo(code);
        User findUser = userRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("isMember", false);

        if (findUser != null) {
            objectNode.put("jwt", userService.createAccessToken(kakaoUserInfo));
            objectNode.put("isMember", true);
        }

        objectNode.putPOJO("oauth_user", kakaoUserInfo);
        return objectNode;
    }
}
