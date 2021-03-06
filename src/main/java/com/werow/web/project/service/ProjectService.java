package com.werow.web.project.service;

import com.werow.web.exception.NotExistResourceException;
import com.werow.web.project.dto.ProjectResponseDto;
import com.werow.web.project.dto.ProjectSettingDto;
import com.werow.web.project.entity.Project;
import com.werow.web.project.entity.ProjectRequest;
import com.werow.web.project.repository.ProjectRepository;
import com.werow.web.project.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final RequestRepository requestRepository;
    private final ProjectRepository projectRepository;

    public ProjectResponseDto initProject(Long requestId, ProjectSettingDto projectSettingDto) {
        ProjectRequest projectRequest = requestRepository.findById(requestId).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 요청이 존재하지 않습니다."));
        Project project = Project.builder()
                .startAt(projectSettingDto.getStartAt())
                .projectRequest(projectRequest)
                .user(projectRequest.getUser())
                .freelancer(projectRequest.getFreelancer())
                .build();
        projectRepository.save(project);
        return project.projectToDto();
    }

    public ProjectResponseDto getProjectDto(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 프로젝트가 존재하지 않습니다."));
        return project.projectToDto();
    }
}
