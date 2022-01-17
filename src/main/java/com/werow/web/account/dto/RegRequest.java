package com.werow.web.account.dto;

import com.werow.web.account.entity.enums.BusinessKind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegRequest {
    private String phone;
    private String introduce;
    private String career;
    private BusinessKind kind;
}
