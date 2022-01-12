package com.werow.web.controller;

import com.werow.web.commons.HttpUtils;
import com.werow.web.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;
    private final HttpUtils httpUtils;
    private final HttpServletRequest request;

    /**
     * 테스트용 (이 부분은 프론트에서 담당할 예정)
     */
    @ApiOperation(value = "카카오 연동 로그인", notes = "[로그인] : Redirect : /, , [회원 가입] Redirect : /register, Parameter : email, photo, provider")
    @GetMapping("/kakao")
    public String provideByKakao() {
        String url = "https://kauth.kakao.com/oauth/authorize?";
        String serverHostName = httpUtils.getServerHostName(request);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("client_id", "e485731066d013c1fc6faaf79bfc6d04");
        paramsMap.put("redirect_uri", serverHostName + "/oauth/kakao");
        paramsMap.put("response_type", "code");
        String queryString = httpUtils.mapToQueryString(paramsMap);

        return "redirect:" + url + queryString;
    }

}
