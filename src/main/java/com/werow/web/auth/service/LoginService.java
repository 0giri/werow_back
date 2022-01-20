package com.werow.web.auth.service;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.account.service.UserService;
import com.werow.web.auth.dto.*;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotCorrectTokenType;
import com.werow.web.exception.NotExistResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;
    private final UserService userService;

    @Transactional
    public AuthResponse oAuth2Login(OAuth2UserInfo userInfo) {
        Optional<User> findUser = userRepository.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider());
        if (findUser.isPresent()) {
            return loginProcess(findUser.get());
        }
        return userInfo;
    }

    @Transactional
    public LoginResponse emailLogin(LoginRequest loginRequest) {
        User findUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new NotExistResourceException("해당 Email을 가진 유저가 존재하지 않습니다."));
        userService.checkPassword(loginRequest.getPassword(), findUser.getPassword());
        return loginProcess(findUser);
    }

    private LoginResponse loginProcess(User user) {
        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshResponse refreshAccessToken() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        if (!tokenInfo.getTokenType().equals(JwtUtils.REFRESH))
            throw new NotCorrectTokenType("Refresh 토큰이 아닙니다.");
        User findUser = userRepository.findById(tokenInfo.getId()).orElseThrow(
                () -> new NotExistResourceException("토큰 정보와 관련된 회원이 존재하지 않습니다."));
        String accessToken = jwtUtils.createAccessToken(findUser);
        return new RefreshResponse(accessToken);
    }
}
