package com.werow.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Long id;
    private String accessToken;
    private String refreshToken;

}
