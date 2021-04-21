package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRecruitRepository extends JpaRepository<CrewRecruit, Long> {

    boolean existsByProject(Project p);

}
