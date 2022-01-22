package com.werow.web.account.controller;

import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.service.FreelancerService;
import com.werow.web.auth.annotation.RoleFreelancer;
import com.werow.web.auth.annotation.RoleUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Api(tags = "Freelancer")
@RestController
@RequestMapping("/freelancers")
@CrossOrigin(origins = "http://localhost:3000")
public class FreelancerController {

    private final FreelancerService freelancerService;

    // ------------------------------------ C ------------------------------------
    @RoleUser
    @ApiOperation(value = "프리랜서 등록", notes = "프리랜서 등록 폼 기반 프리랜서 등록")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void regFreelancer(@RequestBody RegRequest regRequest) {
        freelancerService.registerFreelancer(regRequest);
    }


    // ------------------------------------ R ------------------------------------
//    @RoleManager
    @ApiOperation(value = "모든 프리랜서 조회", notes = "모든 프리랜서 정보 조회")
    @GetMapping
    public ResponseEntity<List<FreelancerDto>> getAllFreelancers() {
        List<FreelancerDto> freelancerDtoList = freelancerService.freelancerListToDtoList();
        return ResponseEntity.ok(freelancerDtoList);
    }

    @RoleFreelancer
    @ApiOperation(value = "JWT 프리랜서 조회", notes = "JWT로 프리랜서 정보 조회")
    @GetMapping(value = "/token",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerDto> getFreelancerByToken() {
        FreelancerDto findFreelancer = freelancerService.getFreelancerDtoByToken();
        return ResponseEntity.ok(findFreelancer);
    }

    @RoleFreelancer
    @ApiOperation(value = "프리랜서 조회", notes = "회원 ID로 프리랜서 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<FreelancerDto> getFreelancerByUserId(@PathVariable Long id) {
        FreelancerDto findFreelancer = freelancerService.getFreelancerDtoByUserId(id);
        return ResponseEntity.ok(findFreelancer);
    }


    // ------------------------------------ U ------------------------------------
    @RoleFreelancer
    @ApiOperation(value = "프리랜서 활성화", notes = "회원 ID로 프리랜서를 활성화")
    @PatchMapping("/{id}/activate")
    public void activateFreelancer(@PathVariable Long id) {
        freelancerService.activateFreelancer(id);
    }

    @RoleFreelancer
    @ApiOperation(value = "프리랜서 비활성화", notes = "회원 ID로 프리랜서를 비활성화")
    @PatchMapping("/{id}/deactivate")
    public void deactivateFreelancer(@PathVariable Long id) {
        freelancerService.deactivateFreelancer(id);
    }


    // ------------------------------------ D ------------------------------------

}
