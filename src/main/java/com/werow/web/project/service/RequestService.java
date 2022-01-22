package com.werow.web.project.service;

import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.account.service.FreelancerService;
import com.werow.web.account.service.UserService;
import com.werow.web.exception.NotExistResourceException;
import com.werow.web.project.dto.RequestDto;
import com.werow.web.project.dto.RequestResponseDto;
import com.werow.web.project.entity.ProjectRequest;
import com.werow.web.project.entity.enums.RequestStatus;
import com.werow.web.project.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final FreelancerService freelancerService;

    public RequestResponseDto createProjectRequest(RequestDto requestDto) {
        User user = userService.getUserById(requestDto.getUserId());
        Freelancer freelancer = freelancerService.findFreelancerByUserId(requestDto.getFreelancerUserId());
        ProjectRequest projectRequest = ProjectRequest.builder()
                .requestInfo(requestDto.getRequestInfo())
                .status(RequestStatus.WAIT)
                .user(user)
                .freelancer(freelancer)
                .build();
        requestRepository.save(projectRequest);
        return projectRequest.requestToResponseDto();
    }

    public void changeRequestStatus(Long id, RequestStatus status) {
        ProjectRequest projectRequest = findRequestById(id);
        projectRequest.changeRequestStatus(status);
    }

    public RequestResponseDto getRequestResponseDto(Long id) {
        ProjectRequest projectRequest = findRequestById(id);
        return projectRequest.requestToResponseDto();
    }

    private ProjectRequest findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 프로젝트 요청이 존재하지 않습니다."));
    }
}
