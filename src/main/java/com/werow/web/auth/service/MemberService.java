package com.werow.web.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {


//    public User oAuthLogin() {
////        String nickname = kakaoUserInfo.getNickname();
//        String email = oAuthUserInfo.getEmail();
//        String photo = oAuthUserInfo.getPhoto();
//
//        User user = userRepository.findByEmail(OAuthUserInfo.getEmail()).orElse(null);
//        String action = "Login";
//
//        if (user == null) {
//
//        }
//
//        log.info("[{}] id: {}, email:{}, role:{}, provider:{}", action, user.getId(), user.getEmail(), user.getRole(), user.getProvider());
//        request.getSession().setAttribute("user", user);
//    }
//
//    public User oAuthJoin(OAuthUserInfo oAuthUserInfo) {
//        String email = oAuthUserInfo.getEmail();
//        String photo = oAuthUserInfo.getPhoto();
//
//        user = new User(email, nickname, encodedPassword, photo, UserRole.USER, AuthProvider.KAKAO);
//        user.setCreatedInfo(httpUtils.getClientIP(request));
//        userRepository.save(user);
//        action = "Join";
//    }


//
//    public String kakaoLogout(String token) throws IOException {
//        HttpURLConnection con = createConnection("https://kauth.kakao.com/oauth/token");
//
//        con.setRequestProperty("Authorization", );
//        // Header
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Accept", "application/json");
//
//        // Request
//        con.connect();
//
//        return "";
//    }

}
