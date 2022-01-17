package com.werow.web.auth.service;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.*;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotJoinedUserException;
import com.werow.web.exception.NotMatchPassword;
import com.werow.web.exception.NotCorrectTokenType;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;

    @Transactional
    public LoginResponse oAuth2Login(OAuth2UserInfo oAuth2UserInfo) {
        User findUser = userRepository.findByEmailAndProvider(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider()).orElseThrow(
                () -> new NotJoinedUserException("미가입 유저입니다."));

        return loginProcess(findUser);
    }

    @Transactional
    public LoginResponse emailLogin(LoginRequest loginRequest) {
        String encodedPassword = BCrypt.hashpw(loginRequest.getPassword(), BCrypt.gensalt());
        User findUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new NotJoinedUserException("해당하는 회원이 없습니다."));
        if (!encodedPassword.equals(findUser.getPassword()))
            throw new NotMatchPassword("비밀번호가 일치하지 않습니다.");
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
                () -> new NotJoinedUserException("토큰 서명과 관련된 회원을 찾지 못했습니다."));
        String accessToken = jwtUtils.createAccessToken(findUser);
        return new RefreshResponse(accessToken);
    }
}
