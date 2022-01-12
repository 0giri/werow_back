package com.werow.web.repository;

import com.werow.web.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);
}
