package com.werow.web.account.service;

import com.werow.web.account.dto.ChangeUserRequest;
import com.werow.web.account.dto.JoinRequest;
import com.werow.web.account.dto.PasswordChangeDto;
import com.werow.web.account.dto.UserDto;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.LoginRequest;
import com.werow.web.auth.dto.LoginResponse;
import com.werow.web.auth.dto.TokenInfo;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;

    /**
     * 유저 등록
     */
    public LoginResponse join(JoinRequest joinRequest) {
        validateDuplicateUser(joinRequest);
        User user = new User(joinRequest);
        userRepository.save(user);

        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    /**
     * 토큰으로 유저 조회
     */
    @Transactional(readOnly = true)
    public User getUserByToken() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        return getUserById(tokenInfo.getId());
    }

    /**
     * ID로 유저 조회
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
    }

    /**
     * 유저 비밀번호 변경
     */
    public void changePassword(Long id, PasswordChangeDto passwordDto) {
        User findUser = getUserById(id);
        checkTokenUser(findUser);
        checkPassword(passwordDto.getCurrentPassword(), findUser.getPassword());
        findUser.changePassword(passwordDto.getNewPassword());
    }

    /**
     * 유저 닉네임 변경
     */
    public void changeNickname(Long id, ChangeUserRequest changeUserRequest) {
        Optional<User> findUserByNickname = userRepository.findByNickname(changeUserRequest.getNickname());
        if (findUserByNickname.isPresent()) {
            throw new DuplicatedUniqueException("이미 존재하는 닉네임입니다.");
        }
        User findUser = getUserById(id);
        checkTokenUser(findUser);
        findUser.changeNickname(changeUserRequest.getNickname());
    }

    /**
     * 유저 프로필사진 변경
     */
    public void changePhoto(Long id, ChangeUserRequest changeUserRequest) {
        User findUser = getUserById(id);
        checkTokenUser(findUser);
        findUser.changePhoto(changeUserRequest.getPhoto());
    }

    /**
     * 유저 활성화
     */
    public void activateUser(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        findUser.activate();
    }

    /**
     * 유저 비활성화
     */
    public void deactivateUser(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        findUser.deactivate();
    }

    /**
     * 유저 삭제
     */
    public void deleteUser(Long id, LoginRequest loginRequest) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        checkTokenUser(findUser);
        checkPassword(loginRequest.getPassword(), findUser.getPassword());
        userRepository.delete(findUser);
    }

    /**
     * 유저 엔티티 리스트를 유저 DTO 리스트로 변환
     */
    @Transactional(readOnly = true)
    public List<UserDto> userListToDtoList() {
        List<User> allUsers = userRepository.findAll();
        return allUsers
                .stream()
                .map(User::userToDto)
                .collect(Collectors.toList());
    }

    /**
     * 토큰의 정보와 접근하고자 하는 유저가 일치하는지 확인
     */
    private void checkTokenUser(User findUser) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        if (tokenInfo.getId() != findUser.getId()) {
            throw new NotEnoughAuthorityException("토큰의 정보와는 다른 유저에 접근하셨습니다.");
        }
    }

    /**
     * 비밀번호 검증
     */
    public void checkPassword(String checkPassword, String currentPassword) {
        if (!BCrypt.checkpw(checkPassword, currentPassword)) {
            throw new NotMatchPassword("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 중복 회원 검사
     */
    private void validateDuplicateUser(JoinRequest joinRequest) {
        Optional<User> findUserByEmailOrNickname =
                userRepository.findByEmailOrNickname(joinRequest.getEmail(), joinRequest.getNickname());
        if (findUserByEmailOrNickname.isPresent()) {
            User findUser = findUserByEmailOrNickname.get();
            if (joinRequest.getEmail().equals(findUser.getEmail())) {
                throw new DuplicatedUniqueException("이미 존재하는 이메일입니다.");
            }
            throw new DuplicatedUniqueException("이미 존재하는 닉네임입니다.");
        }
    }

}
