package com.werow.web.account.controller;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.account.service.UserService;
import com.werow.web.auth.TokenRequired;
import com.werow.web.auth.dto.AuthResponse;
import com.werow.web.auth.dto.JoinRequest;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"User"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AuthResponse> join(JoinRequest joinRequest) {
        log.debug("JoinRequest : {}", joinRequest);
        AuthResponse authResponse = userService.join(joinRequest);
        return ResponseEntity.ok(authResponse);
    }

    @TokenRequired
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
