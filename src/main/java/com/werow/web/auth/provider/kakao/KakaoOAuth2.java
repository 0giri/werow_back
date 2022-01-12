package com.werow.web.auth.provider.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.werow.web.commons.HttpUtils;
import com.werow.web.sub.AuthProvider;
import com.werow.web.dto.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2 {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;
    private final RestTemplate restTemplate;

    /**
     * 카카오 연동하여 유저 정보 반환
     */
    public OAuthUserInfo getKakaoUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getKakaoUserInfoByToken(accessToken);
    }

    /**
     * Authorized Code로 Access Token 요청
     */
    private String getAccessToken(String authCode) {
        String url = "https://kauth.kakao.com/oauth/token";
        String hostName = httpUtils.getServerHostName(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e485731066d013c1fc6faaf79bfc6d04");
        params.add("redirect_uri", hostName + "/oauth/kakao");
        params.add("code", authCode);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(URI.create(url), params, JsonNode.class);
        JsonNode response = responseEntity.getBody();
        return response.get("access_token").asText();
    }

    /**
     * Access Token으로 유저 정보 요청
     */
    public OAuthUserInfo getKakaoUserInfoByToken(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);
        JsonNode response = responseEntity.getBody();

        JsonNode kakaoAccount = response.get("kakao_account");
        String email = kakaoAccount.get("email").asText();
        String photo = kakaoAccount.get("profile").get("profile_image_url").asText();

        return new OAuthUserInfo(email, photo, AuthProvider.KAKAO);
    }

}
