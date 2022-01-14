package com.werow.web.account.service;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.AuthResponse;
import com.werow.web.auth.dto.JoinRequest;
import com.werow.web.auth.jwt.JwtUtils;
import com.werow.web.commons.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpUtils httpUtils;
    private final HttpServletRequest request;


    @Transactional
    public AuthResponse join(JoinRequest joinRequest) {
        String encodedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .email(joinRequest.getEmail())
                .nickname(joinRequest.getNickname())
                .password(encodedPassword)
                .photo(joinRequest.getPhoto())
                .provider(joinRequest.getProvider())
                .build();

        String clientIP = httpUtils.getClientIP(request);
        user.setCreatedInfo(clientIP);
        userRepository.save(user);

        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }
}
