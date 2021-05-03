package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
