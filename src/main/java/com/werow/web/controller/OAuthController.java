package com.werow.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.werow.web.entity.Member;
import com.werow.web.dto.OAuthUserInfo;
import com.werow.web.auth.provider.kakao.KakaoOAuth2;
import com.werow.web.repository.MemberRepository;
import com.werow.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final KakaoOAuth2 kakaoOAuth2;
//    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 프론트에서 이 메소드에 코드를 넘기며 호출
     */
    @GetMapping("/kakao")
    public ObjectNode getKakaoUser(String code) {
        OAuthUserInfo kakaoUserInfo = kakaoOAuth2.getKakaoUserInfo(code);
        Member member = memberRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("isMember", false);

        if (member != null) {
//            String accessToken = jwtTokenUtil.createAccessToken(member);
//            String refreshToken = jwtTokenUtil.createRefreshToken(member);
//            member.updateRefreshToken(refreshToken);
//            objectNode.put("access_token", accessToken);
//            objectNode.put("isMember", true);
        }

        objectNode.putPOJO("oauth_user", kakaoUserInfo);
        return objectNode;
    }
}
