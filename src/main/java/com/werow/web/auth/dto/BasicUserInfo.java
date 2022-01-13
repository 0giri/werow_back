package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicUserInfo {
    private String email;
    private String photo;
    private AuthProvider provider;
    private boolean isMember = false;
    private String accessToken;

    public BasicUserInfo(String email, String photo, AuthProvider provider) {
        this.email = email;
        this.photo = photo;
        this.provider = provider;
    }

    public void setupForMember(String accessToken) {
        this.isMember = true;
        this.accessToken = accessToken;
    }

}
