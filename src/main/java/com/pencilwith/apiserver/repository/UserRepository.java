package com.pencilwith.apiserver.repository;

import com.pencilwith.apiserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNickName(String nickName);
}
