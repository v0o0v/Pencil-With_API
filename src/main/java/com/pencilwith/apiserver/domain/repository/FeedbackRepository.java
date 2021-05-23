package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Feedback;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @EntityGraph(attributePaths = {"owner", "project", "replyList", "replyList.owner"})
    Page<Feedback> findByProjectOrderByCreatedAtDesc(Project project, Pageable pageable);

    void deleteAllByOwner(User user);
}
