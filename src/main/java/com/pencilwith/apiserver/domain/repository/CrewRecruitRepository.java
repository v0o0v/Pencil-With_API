package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.CrewRecruitState;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CrewRecruitRepository extends JpaRepository<CrewRecruit, Long>, QuerydslPredicateExecutor<CrewRecruit> {

    boolean existsByProject(Project p);

    List<CrewRecruit> findByOwnerAndStateNotOrderByCreatedAtDesc(User user, CrewRecruitState state);

}
