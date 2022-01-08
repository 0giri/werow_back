package com.werow.web.user.provider.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.werow.web.commons.HttpUtils;
import com.werow.web.user.domain.AuthProvider;
import com.werow.web.user.dto.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2 {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;

    /**
     * 카카오 연동하여 유저 정보 반환
     */
    public OAuthUserInfo getKakaoUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getUserInfoByToken(accessToken);
    }

    /**
     * Authorized Code로 Access Token 요청
     */
    private String getAccessToken(String authCode) {
        String accessToken = null;
        try {
            // Create Connection
            HttpURLConnection con = httpUtils.createConnection("https://kauth.kakao.com/oauth/token");
            con.setRequestMethod("POST");

            // Set Headers
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // Query Parameter
            String hostName = httpUtils.getServerHostName(request);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("grant_type", "authorization_code");
            paramsMap.put("client_id", "e485731066d013c1fc6faaf79bfc6d04");
            paramsMap.put("redirect_uri", hostName + "/oauth/kakao");
            paramsMap.put("code", authCode);
            httpUtils.setRequestBody(con, paramsMap);

            // Get Response
            JsonNode tokenInfo = httpUtils.executeRequest(con);
            accessToken = tokenInfo.get("access_token").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * Access Token으로 유저 정보 요청
     */
    public OAuthUserInfo getUserInfoByToken(String accessToken) {
        OAuthUserInfo oAuthUserInfo = null;
        try {
            // Create Connection
            HttpURLConnection con = httpUtils.createConnection("https://kapi.kakao.com/v2/user/me");
            con.setRequestMethod("POST");

            // Set Headers
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);

            JsonNode kakaoAccount = httpUtils.executeRequest(con).get("kakao_account");
            String email = kakaoAccount.get("email").asText();
            String photo = kakaoAccount.get("profile").get("profile_image_url").asText();

            oAuthUserInfo = new OAuthUserInfo(email, photo, AuthProvider.KAKAO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oAuthUserInfo;
    }

}
