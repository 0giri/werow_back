package com.werow.web.account.service;

import com.werow.web.account.dto.FreelancerDto;
import com.werow.web.account.dto.RegRequest;
import com.werow.web.account.entity.Freelancer;
import com.werow.web.account.entity.User;
import com.werow.web.account.repository.FreelancerRepository;
import com.werow.web.account.repository.UserRepository;
import com.werow.web.auth.dto.TokenInfo;
import com.werow.web.commons.JwtUtils;
import com.werow.web.exception.NotJoinedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    private final EntityManager em;

    public void registerFreelancer(RegRequest regRequest) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        User findUser = userRepository.findById(tokenInfo.getId()).orElseThrow(
                () -> new NotJoinedUserException("해당 ID를 가진 회원이 없습니다."));
        Freelancer freelancer = new Freelancer(regRequest);
        findUser.regFreelancer(freelancer);
        freelancerRepository.save(freelancer);
    }

    public List<FreelancerDto> freelancerListToDtoList() {
        List<Freelancer> allFreelancers = freelancerRepository.findAll();
        return allFreelancers
                .stream()
                .map(Freelancer::freelancerToDto)
                .collect(Collectors.toList());
    }

    public FreelancerDto findByUserId(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new NotJoinedUserException("해당 ID를 가진 회원이 없습니다."));
        Freelancer freelancer = findUser.getFreelancer();
        return new FreelancerDto(findUser, freelancer);
    }

    public void activateFreelancer() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        Freelancer findFreelancer = freelancerRepository.findById(tokenInfo.getId()).orElseThrow(
                () -> new NotJoinedUserException("해당 ID를 가진 회원이 없습니다."));
        findFreelancer.activate();
    }

    public void deactivateFreelancer() {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        Freelancer findFreelancer = freelancerRepository.findById(tokenInfo.getId()).orElseThrow(
                () -> new NotJoinedUserException("해당 ID를 가진 회원이 없습니다."));
        findFreelancer.deactivate();
    }
}
