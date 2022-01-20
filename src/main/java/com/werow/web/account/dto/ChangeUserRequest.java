package com.werow.web.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserRequest {
    @ApiModelProperty(example = "new nickname")
    private String nickname;
    @ApiModelProperty(example = "new photo URL")
    private String photo;
}
