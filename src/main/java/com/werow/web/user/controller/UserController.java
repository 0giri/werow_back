package com.werow.web.user.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.werow.web.commons.HttpUtils;
import com.werow.web.exception.FailedLoginException;
import com.werow.web.user.domain.User;
import com.werow.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpUtils httpUtils;

    @GetMapping("/kakao-form")
    public String getKakaoLoginForm() {
        String url = "https://kauth.kakao.com/oauth/authorize?";

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("client_id", "e485731066d013c1fc6faaf79bfc6d04");
        paramsMap.put("redirect_uri", "http://localhost:8080/login/kakao");
        paramsMap.put("response_type", "code");

        return "redirect:" + url + httpUtils.mapToQueryString(paramsMap);
    }

    @GetMapping("/kakao")
    public String kakaoLogin(String code) {
        userService.kakaoLogin(code);
        return "redirect:/";
    }


}
