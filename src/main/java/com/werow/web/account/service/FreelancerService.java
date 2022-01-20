package com.werow.web.account.service;

import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.FreelancerRepository;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.TokenInfo;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotExistResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FreelancerService {

    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;
    private final FreelancerRepository freelancerRepository;
    private final UserRepository userRepository;

    /**
     * 유저를 프리랜서로 등록
     */
    public void registerFreelancer(RegRequest regRequest) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        User findUser = userRepository.findById(tokenInfo.getId()).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        Freelancer freelancer = new Freelancer(regRequest);
        findUser.regFreelancer(freelancer);
        freelancerRepository.save(freelancer);
    }

    /**
     * 유저 ID로 프리랜서 조회
     */
    public FreelancerDto findFreelancerByUserId(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        Freelancer freelancer = findUser.getFreelancer();
        return freelancer.freelancerToDto();
    }

    /**
     * 토큰으로 프리랜서 조회
     */
    public FreelancerDto findFreelancerByToken() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        return findFreelancerByUserId(tokenInfo.getId());
    }

    /**
     * 유저 ID로 프리랜서 활성화
     */
    public void activateFreelancer(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        Freelancer freelancer = findUser.getFreelancer();
        freelancer.activate();
    }

    /**
     * 유저 ID로 프리랜서 비활성화
     */
    public void deactivateFreelancer(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotExistResourceException("해당 ID를 가진 유저가 존재하지 않습니다."));
        Freelancer freelancer = findUser.getFreelancer();
        freelancer.deactivate();
    }

    /**
     * 프리랜서 엔티티 리스트를 프리랜서 DTO 리스트로 변환
     */
    public List<FreelancerDto> freelancerListToDtoList() {
        List<Freelancer> allFreelancers = freelancerRepository.findAll();
        return allFreelancers
                .stream()
                .map(Freelancer::freelancerToDto)
                .collect(Collectors.toList());
    }

}
