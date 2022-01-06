package com.werow.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {
    String email;
    String nickname;
    String photo;
}
