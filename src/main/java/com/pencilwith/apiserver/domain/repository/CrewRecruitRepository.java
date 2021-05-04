package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CrewRecruitRepository extends JpaRepository<CrewRecruit, Long>, QuerydslPredicateExecutor<CrewRecruit> {

    boolean existsByProject(Project p);

}
