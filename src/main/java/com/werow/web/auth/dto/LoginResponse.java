package com.werow.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends AuthResponse{

    private Long id;
    private String accessToken;
    private String refreshToken;

}
