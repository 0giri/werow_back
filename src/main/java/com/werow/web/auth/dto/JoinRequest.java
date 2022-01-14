package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinRequest {
    private String email;
    private String nickname;
    private String password;
    private String photo;
    private AuthProvider provider;
}
