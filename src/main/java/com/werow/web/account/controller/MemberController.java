package com.werow.web.account.controller;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.TokenRequired;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"User"})
@RestController
@RequestMapping("/users")
public class MemberController {

    private final UserRepository userRepository;

    @PostMapping
    public Long joinUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @TokenRequired
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
