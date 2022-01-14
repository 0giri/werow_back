package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2UserInfo {
    private String email;
    private String photo;
    private AuthProvider provider;

    public OAuth2UserInfo(String email, String photo, AuthProvider provider) {
        this.email = email;
        this.photo = photo;
        this.provider = provider;
    }

}
