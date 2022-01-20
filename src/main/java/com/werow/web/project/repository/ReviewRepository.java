package com.werow.web.project.repository;

import com.werow.web.account.entity.Freelancer;
import com.werow.web.project.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByFreelancer(Freelancer freelancer);
}
