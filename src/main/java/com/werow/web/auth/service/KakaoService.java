package com.werow.web.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.werow.web.account.entity.enums.AuthProvider;
import com.werow.web.auth.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class KakaoService {

    private final RestTemplate restTemplate;

    @Value("${oauth2.kakao.grant-type}")
    private String grantType;
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    @Value("${oauth2.kakao.redirect-url}")
    private String redirectURL;
    @Value("${oauth2.kakao.base-url}")
    private String baseURL;
    @Value("${oauth2.kakao.user-info-url}")
    private String userInfoURL;

    /**
     * 카카오 연동하여 유저 정보 반환
     */
    public OAuth2UserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getUserInfoByToken(accessToken);
    }

    /**
     * Authorized Code로 Access Token 요청
     */
    private String getAccessToken(String authCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectURL);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(URI.create(baseURL), requestEntity, JsonNode.class);
        JsonNode response = responseEntity.getBody();

        return response.get("access_token").asText();
    }

    /**
     * Access Token으로 유저 정보 요청
     */
    public OAuth2UserInfo getUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(userInfoURL));
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);
        JsonNode response = responseEntity.getBody();

        return responseToUserInfo(response);
    }

    private OAuth2UserInfo responseToUserInfo(JsonNode response) {
        JsonNode kakaoAccount = response.get("kakao_account");
        String email = kakaoAccount.get("email").asText();
        String photo = kakaoAccount.get("profile").get("profile_image_url").asText();
        return new OAuth2UserInfo(email, photo, AuthProvider.KAKAO);
    }

}
