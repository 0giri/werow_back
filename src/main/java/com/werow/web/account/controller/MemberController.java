package com.werow.web.account.controller;

import com.werow.web.account.entity.User;
import com.werow.web.account.repository.MemberRepository;
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
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public Long joinUser(User user) {
        User saveUser = memberRepository.save(user);
        return saveUser.getId();
    }

}
