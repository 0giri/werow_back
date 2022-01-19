package com.werow.web.work.controller;

import com.werow.web.work.dto.WorkDto;
import com.werow.web.work.dto.WorkRequestDto;
import com.werow.web.work.repository.WorkRepository;
import com.werow.web.work.service.WorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Work")
@RestController
@RequestMapping("/works")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkController {

    private final WorkService workService;
    private final WorkRepository workRepository;

    @ApiOperation(value = "작업 생성", notes = "유저, 프리랜서가 연결된 작업 생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkDto> requestWork(@RequestBody WorkRequestDto workRequestDto) {
        WorkDto workDto = workService.requestWork(workRequestDto);
        return ResponseEntity.ok(workDto);
    }
}
