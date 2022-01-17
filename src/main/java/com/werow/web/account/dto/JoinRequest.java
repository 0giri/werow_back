package com.werow.web.account.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {
    private String email;
    private String nickname;
    private String password;
    private String photo;
    private AuthProvider provider;
}
