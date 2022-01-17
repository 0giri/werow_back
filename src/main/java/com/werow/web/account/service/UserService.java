package com.werow.web.account.service;

import com.werow.web.account.dto.JoinRequest;
import com.werow.web.account.dto.PasswordChangeDto;
import com.werow.web.account.dto.UserDto;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.*;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotEnoughAuthorityException;
import com.werow.web.exception.NotExistResourceException;
import com.werow.web.exception.NotJoinedUserException;
import com.werow.web.exception.NotMatchPassword;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;

    public LoginResponse join(JoinRequest joinRequest) {
        String encodedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .email(joinRequest.getEmail())
                .nickname(joinRequest.getNickname())
                .password(encodedPassword)
                .photo(joinRequest.getPhoto())
                .provider(joinRequest.getProvider())
                .build();

        userRepository.save(user);

        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    public void deleteUser(Long id, LoginRequest loginRequest) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotJoinedUserException("해당 ID를 가진 회원이 없습니다."));
        checkTokenUser(findUser);
        if (!loginRequest.getEmail().equals(findUser.getEmail())) {
            throw new NotEnoughAuthorityException("로그인 정보와는 다른 유저에 접근하셨습니다.");
        }
        checkPassword(loginRequest.getPassword(), findUser.getPassword());
        userRepository.delete(findUser);
    }

    public Long changePassword(Long id, PasswordChangeDto passwordDto) {
        User findUser = getUserById(id);
        checkTokenUser(findUser);
        checkPassword(passwordDto.getCurrentPassword(), findUser.getPassword());
        findUser.changePassword(passwordDto.getNewPassword());
        return findUser.getId();
    }


    @Transactional(readOnly = true)
    public List<UserDto> userListToDtoList() {
        List<User> allUsers = userRepository.findAll();
        return allUsers
                .stream()
                .map(User::userToDto)
                .collect(Collectors.toList());
    }

    public Long changePhoto(Long id, String photo) {
        User findUser = getUserById(id);
        findUser.changePhoto(photo);
        return findUser.getId();
    }

    public Long changeNickname(Long id, String nickname) {
        User findUser = getUserById(id);
        findUser.changeNickname(nickname);
        return findUser.getId();
    }

    @Transactional(readOnly = true)
    public User getUserByToken() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        return getUserById(tokenInfo.getId());
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
    }

    private void checkTokenUser(User findUser) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        if (tokenInfo.getId() != findUser.getId()) {
            throw new NotEnoughAuthorityException("토큰의 정보와는 다른 유저에 접근하셨습니다.");
        }
    }

    private void checkPassword(String checkPassword, String currentPassword) {
        if (!BCrypt.checkpw(checkPassword, currentPassword)) {
            throw new NotMatchPassword("비밀번호가 일치하지 않습니다.");
        }
    }

}
