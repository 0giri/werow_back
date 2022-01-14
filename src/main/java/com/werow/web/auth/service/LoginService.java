package com.werow.web.auth.service;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.AuthResponse;
import com.werow.web.auth.dto.EmailLoginRequest;
import com.werow.web.auth.dto.OAuth2UserInfo;
import com.werow.web.auth.jwt.JwtUtils;
import com.werow.web.exception.NotJoinedUser;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthResponse oAuth2Login(OAuth2UserInfo oAuth2UserInfo) {
        Optional<User> findUser = userRepository.findByEmailAndProvider(oAuth2UserInfo.getEmail(),oAuth2UserInfo.getProvider());

        return loginProcess(findUser);
    }

    @Transactional
    public AuthResponse emailLogin(EmailLoginRequest emailLoginRequest) {
        String encodedPassword = BCrypt.hashpw(emailLoginRequest.getPassword(), BCrypt.gensalt());
        Optional<User> findUser = userRepository.findByEmailAndPassword(emailLoginRequest.getEmail(), encodedPassword);

        return loginProcess(findUser);
    }

    private AuthResponse loginProcess(Optional<User> findUser) {
        if (findUser.isEmpty()) {
            throw new NotJoinedUser("미가입 유저입니다.");
        }

        User user = findUser.get();
        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }
}
