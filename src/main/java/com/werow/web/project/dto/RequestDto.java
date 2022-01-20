package com.werow.web.project.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @ApiModelProperty(example = "1")
    private Long userId;
    @ApiModelProperty(example = "1")
    private Long freelancerUserId;
    @ApiModelProperty(example = "1000000")
    private Integer price;
    @ApiModelProperty(example = "웹페이지 개발 및 운영")
    private String requestInfo;
    @ApiModelProperty(example = "true")
    private Boolean inOffice;
}
