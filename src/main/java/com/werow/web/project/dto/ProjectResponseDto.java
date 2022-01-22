package com.werow.web.project.dto;

import com.werow.web.project.entity.Project;
import com.werow.web.project.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDto {
    private Long projectId;
    private Long requestId;
    private Long userId;
    private Long freelancerId;
    private String requestInfo;
    private ProjectStatus status;
    private LocalDate startAt;

    public ProjectResponseDto(Project project) {
        this.projectId = project.getId();
        this.requestId = project.getProjectRequest().getId();
        this.userId = project.getUser().getId();
        this.freelancerId = project.getFreelancer().getId();
        this.requestInfo = project.getProjectRequest().getRequestInfo();
        this.status = project.getStatus();
        this.startAt = project.getStartAt();
    }
}
