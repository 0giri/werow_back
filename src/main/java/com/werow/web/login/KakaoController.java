package com.werow.web.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.werow.web.exception.FailedGetResponseException;
import com.werow.web.exception.FailedLoginException;
import com.werow.web.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
@Slf4j
public class KakaoController {

    @GetMapping("/kakao-form")
    public String getKakaoLoginForm() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=e485731066d013c1fc6faaf79bfc6d04");
        url.append("&redirect_uri=http://localhost:8080/login/kakao");
        url.append("&response_type=code");

        return "redirect:" + url.toString();
    }

    @GetMapping("/kakao")
    @ResponseBody
    public String kakaoLogin(String code) {

        String result = "";
        try {
            JsonNode tokenInfo = getTokens(code);
            String accessToken = tokenInfo.get("access_token").asText();

            JsonNode userInfo = getUserInfo(accessToken);


            User user = new User();
            System.out.println(userInfo.get("kakao_account"));


            System.out.println(userInfo.get("id"));
            System.out.println(userInfo.get("connected_at"));
            System.out.println(userInfo.get("properties"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 응답받은 Code로 Token정보 요청하고 JsonNode에 저장
     *
     * @Return (access_token, token_type, refresh_token, expires_in, scope, refresh_token_expires_in)
     */
    private JsonNode getTokens(String code) throws IOException, FailedLoginException {
        // Create Connection
        HttpURLConnection con = createConnection("https://kauth.kakao.com/oauth/token");
        con.setRequestMethod("POST");

        // Set Headers
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // Query Parameter
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "authorization_code");
        paramsMap.put("client_id", "e485731066d013c1fc6faaf79bfc6d04");
        paramsMap.put("redirect_uri", "http://localhost:8080/login/kakao");
        paramsMap.put("code", code);

        String queryString = mapToQueryString(paramsMap);

        OutputStream os = con.getOutputStream();
        os.write(queryString.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        // Do Request
        con.connect();

        // Get Response
        JsonNode tokenInfo = responseToJsonNode(con);

        con.disconnect();
        return tokenInfo;
    }

    /**
     * 응답받은 Access Token으로 유저정보 요청하고 JsonNode에 저장
     *
     * @Return (Long id, Datetime connected_at, String properties, String kakao_account)
     */
    private JsonNode getUserInfo(String accessToken) throws IOException, FailedLoginException {
        // Create Connection
        HttpURLConnection con = createConnection("https://kapi.kakao.com/v2/user/me");
        con.setRequestMethod("POST");

        // Set Headers
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);

        // Do Request
        con.connect();

        // Get Response
        JsonNode userInfo = responseToJsonNode(con);

        con.disconnect();
        return userInfo;
    }

    public String kakaoLogout(String token) throws IOException {
        HttpURLConnection con = createConnection("https://kauth.kakao.com/oauth/token");

//        con.setRequestProperty("Authorization", );
        // Header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");

        // Request
        con.connect();

        return "";
    }

    /**
     * 외부 Host와의 Connection 생성
     */
    private HttpURLConnection createConnection(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        return con;
    }

    /**
     * 파라미터가 담긴 Map을 쿼리스트링 형식의 String으로 변환
     */
    private String mapToQueryString(Map<String, Object> paramsMap) {
        StringBuffer sb = new StringBuffer();
        for (String key : paramsMap.keySet()) {
            sb.append(key).append("=").append((String) paramsMap.get(key)).append("&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        return sb.toString();
    }

    /**
     * JSON 형식의 응답 결과를 파싱해 JsonNode로 변환
     */
    private JsonNode responseToJsonNode(HttpURLConnection con) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(con.getInputStream());
    }

}
