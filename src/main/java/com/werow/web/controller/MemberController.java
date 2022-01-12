package com.werow.web.controller;

import com.werow.web.entity.Member;
import com.werow.web.repository.MemberRepository;
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
    public Long joinUser(Member user) {
        Member saveUser = memberRepository.save(user);
        return saveUser.getId();
    }

}
