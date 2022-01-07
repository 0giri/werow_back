package com.werow.web.user.service;

import com.werow.web.commons.HttpUtils;
import com.werow.web.user.domain.AuthProvider;
import com.werow.web.user.domain.User;
import com.werow.web.user.domain.UserRole;
import com.werow.web.user.dto.KakaoUserInfo;
import com.werow.web.user.provider.kakao.KakaoOAuth2;
import com.werow.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final HttpUtils httpUtils;
    private final UserRepository userRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    public void kakaoLogin(String authorizedCode) {
        KakaoUserInfo kakaoUserInfo = kakaoOAuth2.getKakaoUserInfo(authorizedCode);
        String email = kakaoUserInfo.getEmail();
        String nickname = kakaoUserInfo.getNickname();
        String photo = kakaoUserInfo.getPhoto();

        User user = userRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);
        String action = "Login";

        if (user == null) {
            String encodedPassword = passwordEncoder.encode(email);
            user = new User(email, nickname, encodedPassword, photo, UserRole.USER, AuthProvider.KAKAO);
            user.setCreatedInfo(httpUtils.getClientIP(request));
            userRepository.save(user);
            action = "Join";
        }

        log.info("[{}] id: {}, email:{}, role:{}, provider:{}",action, user.getId(), user.getEmail(), user.getRole(), user.getProvider());
        request.getSession().setAttribute("user", user);
    }

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
