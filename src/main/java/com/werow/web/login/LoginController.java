package com.werow.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

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
    public String getKakaoCode(String code) {
        System.out.println("code = " + code);
        return code;
    }

}
