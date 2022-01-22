package com.werow.web.project.service;

import com.werow.web.exception.BadValueException;
import com.werow.web.exception.NotExistResourceException;
import com.werow.web.project.dto.ProjectResponseDto;
import com.werow.web.project.dto.ProjectSettingDto;
import com.werow.web.project.entity.Project;
import com.werow.web.project.entity.ProjectRequest;
import com.werow.web.project.entity.enums.ProjectStatus;
import com.werow.web.project.repository.ProjectRepository;
import com.werow.web.project.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final RequestRepository requestRepository;
    private final ProjectRepository projectRepository;

    public ProjectResponseDto initProject(Long requestId, ProjectSettingDto projectSettingDto) {
        ProjectRequest projectRequest = requestRepository.findById(requestId).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 요청이 존재하지 않습니다."));
        LocalDate startAt = projectSettingDto.getStartAt();
        LocalDate now = LocalDate.now();
        ProjectStatus status = ProjectStatus.YET;
        if (startAt.isBefore(now)) {
            throw new BadValueException("시작일은 오늘부터 설정 가능합니다.");
        }
        if (startAt.equals(now)) {
            status = ProjectStatus.ON;
        }
        Project project = Project.builder()
                .status(status)
                .startAt(startAt)
                .projectRequest(projectRequest)
                .user(projectRequest.getUser())
                .freelancer(projectRequest.getFreelancer())
                .build();
        return project.projectToDto();
    }

    public ProjectResponseDto getProjectDto(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 프로젝트가 존재하지 않습니다."));
        return project.projectToDto();
    }
}
