package com.werow.web.dto;

import com.werow.web.entity.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserInfo {
    String email;
    String photo;
    AuthProvider provider;
}
