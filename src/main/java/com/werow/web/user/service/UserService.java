package com.werow.web.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.werow.web.commons.HttpUtils;
import com.werow.web.user.domain.AuthProvider;
import com.werow.web.user.dto.OAuthUserInfo;
import com.werow.web.user.provider.kakao.KakaoOAuth2;
import com.werow.web.user.repository.UserRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private final HttpServletResponse response;

    public String createAccessToken(OAuthUserInfo kakaoUserInfo) {
        Date now = new Date();
        Long expiredTime = 1000 * 60L * 60L * 6L;
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .setSubject("Werow Web")
                .setIssuer("0giri")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .claim("email", kakaoUserInfo.getEmail())
                .claim("provider", kakaoUserInfo.getProvider()) // !!
                .signWith(SignatureAlgorithm.HS256, "0giri")
                .compact();
    }

//    public String createRefreshToken() {
//        Date now = new Date();
//        return Jwts.builder()
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }


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
