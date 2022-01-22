package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuth2Response {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private Boolean isMember;
    private String email;
    private String photo;
    private AuthProvider provider;

    public OAuth2Response(LoginResponse loginResponse) {
        this.isMember = true;
        this.id = loginResponse.getId();
        this.accessToken = loginResponse.getAccessToken();
        this.refreshToken = loginResponse.getRefreshToken();
    }

    public OAuth2Response(OAuth2UserInfo userInfo) {
        this.isMember = false;
        this.email = userInfo.getEmail();
        this.photo = userInfo.getPhoto();
        this.provider = userInfo.getProvider();
    }
}
