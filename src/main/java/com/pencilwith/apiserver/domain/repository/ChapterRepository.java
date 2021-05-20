package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
