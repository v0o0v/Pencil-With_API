package com.pencilwith.apiserver.auth.repository;

import com.pencilwith.apiserver.common.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}
