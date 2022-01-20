package com.werow.web.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @ApiModelProperty(example = "werow@email.com")
    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @ApiModelProperty(example = "1q2w3e4r!")
    @NotNull
    @Size(min = 3, max = 50)
    private String password;

}
