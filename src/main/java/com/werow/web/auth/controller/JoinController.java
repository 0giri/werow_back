package com.werow.web.auth.controller;

import com.werow.web.auth.dto.AuthResponse;
import com.werow.web.auth.dto.JoinRequest;
import com.werow.web.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/join")
public class JoinController {

    private final JoinService joinService;


}
