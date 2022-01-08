package com.werow.web.user.dto;

import com.werow.web.user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserInfo {
    String email;
    String photo;
    AuthProvider provider;
}
