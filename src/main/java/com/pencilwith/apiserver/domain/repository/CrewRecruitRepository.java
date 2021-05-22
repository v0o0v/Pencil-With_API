package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.CrewRecruitState;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface CrewRecruitRepository extends JpaRepository<CrewRecruit, Long>, QuerydslPredicateExecutor<CrewRecruit> {

    boolean existsByProject(Project p);

    @EntityGraph(attributePaths = {"owner", "project", "genre"})
    List<CrewRecruit> findByOwnerAndStateNotOrderByCreatedAtDesc(User user, CrewRecruitState state);

    @EntityGraph(attributePaths = {"owner", "project", "genre"})
    Page<CrewRecruit> findAll(Predicate predicate, Pageable pageable);

    List<CrewRecruit> findByCreatedAtBeforeAndState(LocalDateTime before, CrewRecruitState crewRecruitState);

    void deleteAllByProject(Project project);
}
