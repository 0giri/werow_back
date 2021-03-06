package com.werow.web.account.dto;

import com.werow.web.account.entity.enums.BusinessKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegRequest {
    @ApiModelProperty(example = "010-2083-8745")
    private String phone;
    @ApiModelProperty(example = "안녕하세요")
    private String introduce;
    @ApiModelProperty(example = "축가 10만원, 레슨 회당 4만원 ~")
    private String priceInfo;
    @ApiModelProperty(example = "경력 없음")
    private String career;
    @ApiModelProperty(example = "MUSIC")
    private BusinessKind kind;
}
