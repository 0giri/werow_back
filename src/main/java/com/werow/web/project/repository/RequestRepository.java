package com.werow.web.project.repository;

import com.werow.web.project.entity.ProjectRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<ProjectRequest, Long> {
}
