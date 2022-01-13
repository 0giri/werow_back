package com.werow.web.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.werow.web.commons.HttpUtils;
import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.auth.dto.BasicUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class KakaoService {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;
    private final RestTemplate restTemplate;

    @Value("${oauth2.provider.kakao.base-url}")
    private String baseURL;
    @Value("${oauth2.provider.kakao.grant-type}")
    private String grantType;
    @Value("${oauth2.provider.kakao.client-id}")
    private String clientId;
    @Value("${oauth2.provider.kakao.redirect-url}")
    private String redirectURL;
    @Value("${oauth2.provider.kakao.user-info-url}")
    private String userInfoURL;

    /**
     * 카카오 연동하여 유저 정보 반환
     */
    public BasicUserInfo getKakaoUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getKakaoUserInfoByToken(accessToken);
    }

    /**
     * Authorized Code로 Access Token 요청
     */
    private String getAccessToken(String authCode) {
        String hostName = httpUtils.getServerHostName(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectURL);
        params.add("code", authCode);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(URI.create(baseURL), params, JsonNode.class);
        JsonNode response = responseEntity.getBody();
        return response.get("access_token").asText();
    }

    /**
     * Access Token으로 유저 정보 요청
     */
    public BasicUserInfo getKakaoUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(userInfoURL));
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);
        JsonNode response = responseEntity.getBody();

        JsonNode kakaoAccount = response.get("kakao_account");
        String email = kakaoAccount.get("email").asText();
        String photo = kakaoAccount.get("profile").get("profile_image_url").asText();

        return new BasicUserInfo(email, photo, AuthProvider.KAKAO);
    }

}
