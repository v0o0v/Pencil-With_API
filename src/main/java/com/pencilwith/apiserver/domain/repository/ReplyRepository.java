package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Reply;
import com.pencilwith.apiserver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    void deleteAllByOwner(User owner);

}
