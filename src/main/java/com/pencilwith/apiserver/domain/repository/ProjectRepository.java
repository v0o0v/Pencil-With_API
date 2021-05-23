package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.ProjectStatus;
import com.pencilwith.apiserver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwnerAndStatus(User owner, ProjectStatus status);

    void deleteAllByOwner(User owner);
}
