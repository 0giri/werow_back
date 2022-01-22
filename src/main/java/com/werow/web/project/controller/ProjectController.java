package com.werow.web.project.controller;

import com.werow.web.auth.annotation.RoleFreelancer;
import com.werow.web.auth.annotation.RoleUser;
import com.werow.web.project.dto.ProjectResponseDto;
import com.werow.web.project.dto.ProjectSettingDto;
import com.werow.web.project.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Project")
@RestController
@RequestMapping("/projects")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    @RoleFreelancer
    @ApiOperation(value = "프로젝트 생성", notes = "프로젝트 요청의 ID로 프로젝트 생성")
    @PostMapping(value = "/{requestId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDto> setupProject(@PathVariable Long requestId, @RequestBody ProjectSettingDto projectSettingDto) {
        ProjectResponseDto projectResponseDto = projectService.initProject(requestId, projectSettingDto);
        return ResponseEntity.ok(projectResponseDto);
    }

    @RoleUser
    @ApiOperation(value = "프로젝트 조회", notes = "프로젝트의 ID로 프로젝트 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProjectDto(@PathVariable Long projectId) {
        ProjectResponseDto projectResponseDto = projectService.getProjectDto(projectId);
        return ResponseEntity.ok(projectResponseDto);
    }

}
