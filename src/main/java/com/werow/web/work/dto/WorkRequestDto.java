package com.werow.web.work.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkRequestDto {
    private Long userId;
    private Long freelancerId;
    private String workInfo;
}
