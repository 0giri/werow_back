package com.werow.web.account.repository;

import com.werow.web.account.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
//    Page<Freelancer> findFreelancerForPage();
}
