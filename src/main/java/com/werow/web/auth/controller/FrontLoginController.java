package com.werow.web.auth.controller;

import com.werow.web.commons.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/front")
public class FrontLoginController {

    private final HttpUtils httpUtils;
    private final HttpServletRequest request;

    /**
     * 테스트용 (이 부분은 프론트에서 담당할 예정)
     */
    @ApiIgnore
    @GetMapping("/kakao")
    public String provideByKakao() {
        String url = "https://kauth.kakao.com/oauth/authorize?";
        String serverHostName = httpUtils.getServerHostName(request);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("client_id", "b49d403eab459f2dcb5d7b635c14139b");
        paramsMap.put("redirect_uri", serverHostName + "/api/oauth2/kakao");
        paramsMap.put("response_type", "code");
        String queryString = httpUtils.mapToQueryString(paramsMap);
        System.out.println(queryString);
        return "redirect:" + url + queryString;

    }

}
