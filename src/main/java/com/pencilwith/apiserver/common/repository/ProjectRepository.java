package com.pencilwith.apiserver.common.repository;

import com.pencilwith.apiserver.common.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
