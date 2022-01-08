package com.werow.web.user.controller;

import com.werow.web.user.domain.User;
import com.werow.web.user.repository.UserRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public Long joinUser(User user) {
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

}
