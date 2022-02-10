package com.werow.web.account.controller;

import com.werow.web.account.dto.ChangeUserRequest;
import com.werow.web.account.dto.JoinRequest;
import com.werow.web.account.dto.PasswordChangeDto;
import com.werow.web.account.dto.UserDto;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.account.service.UserService;
import com.werow.web.auth.annotation.RoleManager;
import com.werow.web.auth.annotation.RoleUser;
import com.werow.web.auth.dto.LoginRequest;
import com.werow.web.auth.dto.LoginResponse;
import com.werow.web.exception.DuplicatedUniqueException;
import com.werow.web.exception.NotExistResourceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "User")
@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpServletRequest request;

    // ------------------------------------ C ------------------------------------
    @ApiOperation(value = "유저 등록", notes = "새로운 유저 등록")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> join(@RequestBody JoinRequest joinRequest) {
        LoginResponse loginResponse = userService.join(joinRequest);
        return ResponseEntity.ok(loginResponse);
    }


    // ------------------------------------ R ------------------------------------
    @RoleManager
    @ApiOperation(value = "모든 유저 조회", notes = "모든 유저 정보 조회")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.userListToDtoList();
        return ResponseEntity.ok(userDtoList);
    }

    @RoleUser
    @ApiOperation(value = "유저 조회 (토큰)", notes = "현재 요청에 담긴 토큰의 소유자 정보 조회")
    @GetMapping("/token")
    public ResponseEntity<UserDto> getUserInfoByToken() {
        User findUser = userService.getUserByToken();
        return ResponseEntity.ok(findUser.userToDto());
    }

    @RoleManager
    @ApiOperation(value = "유저 조회 (ID)", notes = "ID로 특정 유저 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        return ResponseEntity.ok(findUser.userToDto());
    }

    @ApiOperation(value = "유저 EMAIL 중복 검사", notes = "해당 EMAIL을 가진 유저가 존재하는지 확인")
    @GetMapping("/check/email/{email}")
    public void checkUserByEmail(@PathVariable String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new DuplicatedUniqueException("중복되는 이메일 주소입니다.");
        }
    }

    @ApiOperation(value = "유저 닉네임 중복 검사", notes = "해당 닉네임을 가진 유저가 존재하는지 확인")
    @GetMapping("/check/nickname/{nickname}")
    public void checkUserByNickname(@PathVariable String nickname) {
        Optional<User> findUser = userRepository.findByNickname(nickname);
        if (findUser.isPresent()) {
            throw new DuplicatedUniqueException("중복되는 닉네임입니다.");
        }
    }


    // ------------------------------------ U ------------------------------------
    @RoleUser
    @ApiOperation(value = "유저 닉네임 변경 (ID)", notes = "ID로 유저 조회하여 닉네임 변경")
    @PatchMapping(value = "/{id}/nickname", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeUserNickname(@PathVariable Long id, @RequestBody ChangeUserRequest changeUserRequest) {
        userService.changeNickname(id, changeUserRequest);
    }

    @RoleUser
    @ApiOperation(value = "유저 프로필사진 변경 (ID)", notes = "ID로 유저 조회하여 프로필사진 변경")
    @PatchMapping(value = "/{id}/photo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeUserPhoto(@PathVariable Long id, @RequestBody ChangeUserRequest changeUserRequest) {
        userService.changePhoto(id, changeUserRequest);
    }

    @RoleUser
    @ApiOperation(value = "유저 비밀번호 변경 (ID)", notes = "ID로 유저 조회하여 비밀번호 변경")
    @PatchMapping(value = "/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordDto) {
        userService.changePassword(id, passwordDto);
    }

    @RoleManager
    @ApiOperation(value = "유저 활성화 (ID)", notes = "유저 계정을 활성화")
    @PatchMapping("/{id}/activate")
    public void activateUser(@PathVariable Long id) {
        userService.activateUser(id);
    }

    @RoleManager
    @ApiOperation(value = "유저 비활성화 (ID)", notes = "유저 계정을 휴면 계정으로 전환")
    @PatchMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }


    // ------------------------------------ D ------------------------------------
    @RoleUser
    @ApiOperation(value = "유저 삭제 (ID)", notes = "ID로 유저 조회하여 삭제")
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable Long id, @RequestBody LoginRequest loginRequest) {
        userService.deleteUser(id, loginRequest);
    }

}
