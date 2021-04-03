package com.pencilwith.apiserver.repository;

import com.pencilwith.apiserver.model.entity.Authority;
import com.pencilwith.apiserver.model.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findAuthorityByType(AuthorityType authorityType);
}
