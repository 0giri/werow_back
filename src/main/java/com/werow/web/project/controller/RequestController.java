package com.werow.web.project.controller;

import com.werow.web.auth.annotation.RoleFreelancer;
import com.werow.web.auth.annotation.RoleUser;
import com.werow.web.project.dto.RequestDto;
import com.werow.web.project.dto.RequestResponseDto;
import com.werow.web.project.entity.enums.RequestStatus;
import com.werow.web.project.service.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Project Request")
@RestController
@RequestMapping("/requests")
@CrossOrigin(origins = "http://localhost:3000")
public class RequestController {

    private final RequestService requestService;

    @RoleUser
    @ApiOperation(value = "프로젝트 요청 생성", notes = "프리랜서에게 요청할 프로젝트 요청 생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestResponseDto> createProjectRequest(@RequestBody RequestDto requestDto) {
        RequestResponseDto requestResponseDto = requestService.createProjectRequest(requestDto);
        return ResponseEntity.ok(requestResponseDto);
    }

    @RoleUser
    @ApiOperation(value = "프로젝트 요청 조회", notes = "프로젝트 요청의 ID로 프로젝트 요청 조회")
    @GetMapping("/{id}")
    public ResponseEntity<RequestResponseDto> getProjectRequest(@PathVariable Long id) {
        RequestResponseDto requestResponseDto = requestService.getRequestResponseDto(id);
        return ResponseEntity.ok(requestResponseDto);
    }

    @RoleUser
    @ApiOperation(value = "프로젝트 요청 취소", notes = "프로젝트 요청의 ID로 유저가 요청을 취소")
    @PatchMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cancelProjectRequest(@PathVariable Long id) {
        requestService.changeRequestStatus(id, RequestStatus.CANCEL);
    }

    @RoleFreelancer
    @ApiOperation(value = "프로젝트 승낙", notes = "프로젝트 요청의 ID로 프리랜서가 요청을 승낙")
    @PatchMapping(value = "/{id}/accept", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void acceptProjectRequest(@PathVariable Long id) {
        requestService.changeRequestStatus(id, RequestStatus.ACCEPT);
    }

    @RoleFreelancer
    @ApiOperation(value = "프로젝트 거절", notes = "프로젝트 요청의 ID로 프리랜서가 요청을 거절")
    @PatchMapping(value = "/{id}/refuse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void refuseProjectRequest(@PathVariable Long id) {
        requestService.changeRequestStatus(id, RequestStatus.REFUSE);
    }

}
