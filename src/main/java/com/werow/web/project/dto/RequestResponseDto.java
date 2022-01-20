package com.werow.web.project.dto;

import com.werow.web.project.entity.ProjectRequest;
import com.werow.web.project.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDto {
    private Long requestId;
    private Long userId;
    private Long freelancerId;
    private Integer price;
    private String requestInfo;
    private Boolean inOffice;
    private RequestStatus status;

    public RequestResponseDto(ProjectRequest projectRequest) {
        this.requestId = projectRequest.getId();
        this.userId = projectRequest.getUser().getId();
        this.freelancerId = projectRequest.getFreelancer().getId();
        this.price = projectRequest.getPrice();
        this.requestInfo = projectRequest.getRequestInfo();
        this.inOffice = projectRequest.getInOffice();
        this.status = projectRequest.getStatus();
    }
}
