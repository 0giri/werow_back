package com.werow.web.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDto {
    @ApiModelProperty(example = "1q2w3e4r!")
    private String currentPassword;
    @ApiModelProperty(example = "5t6y7u8i!")
    private String newPassword;
}
