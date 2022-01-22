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
    private String requestInfo;
    private RequestStatus status;

    public RequestResponseDto(ProjectRequest projectRequest) {
        this.requestId = projectRequest.getId();
        this.userId = projectRequest.getUser().getId();
        this.freelancerId = projectRequest.getFreelancer().getId();
        this.requestInfo = projectRequest.getRequestInfo();
        this.status = projectRequest.getStatus();
    }
}
