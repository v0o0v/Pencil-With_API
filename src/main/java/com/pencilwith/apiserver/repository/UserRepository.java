package com.pencilwith.apiserver.repository;

import com.pencilwith.apiserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<User, Long> {

    User findAccountByNickName(String nickName);
}
