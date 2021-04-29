package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findProjectByOwnerId(String id);
}
