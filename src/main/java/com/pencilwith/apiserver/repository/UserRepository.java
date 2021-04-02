package com.pencilwith.apiserver.repository;

import com.pencilwith.apiserver.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNickname(String nickname);

    Optional<User> findUserByUsername(String username);
}
