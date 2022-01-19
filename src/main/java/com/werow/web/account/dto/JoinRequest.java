package com.werow.web.account.dto;

import com.werow.web.account.entity.enums.AuthProvider;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {
    @ApiModelProperty(example = "werow@email.com")
    private String email;
    @ApiModelProperty(example = "0giri")
    private String nickname;
    @ApiModelProperty(example = "1q2w3e4r!")
    private String password;
    @ApiModelProperty(example = "http://naver.me/GrMjMFBO")
    private String photo;
    @ApiModelProperty(example = "EMAIL")
    private AuthProvider provider;
}
