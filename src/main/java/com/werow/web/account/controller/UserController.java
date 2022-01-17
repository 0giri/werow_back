package com.werow.web.account.controller;

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
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원 정보 조회")
    @RoleManager
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.userListToDtoList();
        return ResponseEntity.ok(userDtoList);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입 폼 기반 회원 등록")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> join(@RequestBody JoinRequest joinRequest) {
        LoginResponse loginResponse = userService.join(joinRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "JWT 회원 조회", notes = "현재 JWT의 소유자 정보 조회")
    @GetMapping("/token")
    public ResponseEntity<UserDto> getUserInfoByToken() {
        User findUser = userService.getUserByToken();
        return ResponseEntity.ok(findUser.userToDto());
    }

    @ApiOperation(value = "회원 조회", notes = "ID로 특정 회원 정보 조회")
    @RoleUser
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        return ResponseEntity.ok(findUser.userToDto());
    }

    @ApiOperation(value = "닉네임 변경", notes = "ID로 회원 조회하여 닉네임 변경")
    @RoleUser
    @PatchMapping(value = "/{id}/nickname", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        return userService.changeNickname(id, nickname);
    }

    @ApiOperation(value = "프로필 사진 변경", notes = "ID로 회원 조회하여 프로필 사진 변경")
    @RoleUser
    @PatchMapping(value = "/{id}/photo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long changePhoto(@PathVariable Long id, @RequestBody String photo) {
        return userService.changePhoto(id, photo);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "ID로 회원 조회하여 프로필 사진 변경")
    @RoleUser
    @PatchMapping(value = "/{id}/password",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordDto) {
        return userService.changePassword(id, passwordDto);
    }

    @ApiOperation(value = "회원 삭제", notes = "ID로 회원 조회하여 삭제")
    @RoleUser
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable Long id, @RequestBody LoginRequest loginRequest) {
        userService.deleteUser(id, loginRequest);
    }
}
