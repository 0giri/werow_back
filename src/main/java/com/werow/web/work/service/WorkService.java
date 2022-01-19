package com.werow.web.work.service;

import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.FreelancerRepository;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.exception.NotExistResourceException;
import com.werow.web.work.dto.WorkDto;
import com.werow.web.work.dto.WorkRequestDto;
import com.werow.web.work.entity.WorkRequest;
import com.werow.web.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkService {

    private final UserRepository userRepository;
    private final FreelancerRepository freelancerRepository;
    private final WorkRepository workRepository;

    public WorkDto requestWork(WorkRequestDto workRequestDto) {
        User user = userRepository.findById(workRequestDto.getUserId()).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        Freelancer freelancer = freelancerRepository.findById(workRequestDto.getFreelancerId()).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));

        WorkRequest workRequest = WorkRequest.builder()
                .workInfo(workRequestDto.getWorkInfo())
                .user(user)
                .freelancer(freelancer)
                .build();

//        workRepository.save()
        return null;
    }
}
