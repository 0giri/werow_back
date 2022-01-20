package com.werow.web.account.controller;

import com.werow.web.account.dto.ChangeUserDto;
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
import com.werow.web.exception.NotExistResourceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "User")
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

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


    // ------------------------------------ U ------------------------------------
    @RoleUser
    @ApiOperation(value = "유저 닉네임 변경 (ID)", notes = "ID로 유저 조회하여 닉네임 변경")
    @PatchMapping(value = "/{id}/nickname", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeUserNickname(@PathVariable Long id, @RequestBody ChangeUserDto changeUserDto) {
        userService.changeNickname(id, changeUserDto);
    }

    @RoleUser
    @ApiOperation(value = "유저 프로필사진 변경 (ID)", notes = "ID로 유저 조회하여 프로필사진 변경")
    @PatchMapping(value = "/{id}/photo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeUserPhoto(@PathVariable Long id, @RequestBody ChangeUserDto changeUserDto) {
        userService.changePhoto(id, changeUserDto);
    }

    @RoleUser
    @ApiOperation(value = "유저 비밀번호 변경 (ID)", notes = "ID로 유저 조회하여 비밀번호 변경")
    @PatchMapping(value = "/{id}/password",consumes = MediaType.APPLICATION_JSON_VALUE)
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
