package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
