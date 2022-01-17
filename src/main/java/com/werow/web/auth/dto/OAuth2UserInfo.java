package com.werow.web.auth.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2UserInfo {
    private String email;
    private String photo;
    private AuthProvider provider;

}
