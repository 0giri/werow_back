package com.werow.web.user.controller;

import com.werow.web.commons.HttpUtils;
import com.werow.web.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"User"})
@Controller
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpUtils httpUtils;

    @ApiOperation(value = "카카오 연동 로그인", notes = "게스트 : 회원가입 후 로그인 처리, 회원 : 로그인 처리")
    @GetMapping("/kakao-form")
    public String getKakaoLoginForm() {
        String url = "https://kauth.kakao.com/oauth/authorize?";

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("client_id", "e485731066d013c1fc6faaf79bfc6d04");
        paramsMap.put("redirect_uri", "http://localhost:8888/login/kakao");
        paramsMap.put("response_type", "code");

        return "redirect:" + url + httpUtils.mapToQueryString(paramsMap);
    }

    @ApiIgnore
    @GetMapping("/kakao")
    public String kakaoLogin(String code) {
        userService.kakaoLogin(code);
        return "redirect:/";
    }

}
