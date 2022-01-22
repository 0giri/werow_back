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
    private ProjectStatus status;
    private LocalDate startAt;
    private RequestResponseDto requestResponseDto;

    public ProjectResponseDto(Project project) {
        this.projectId = project.getId();
        this.status = project.getStatus();
        this.startAt = project.getStartAt();
        this.requestResponseDto = project.getProjectRequest().requestToResponseDto();
    }
}
