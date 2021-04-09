package com.pencilwith.apiserver.auth.repository;

import com.pencilwith.apiserver.common.model.entity.Authority;
import com.pencilwith.apiserver.common.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findAuthorityByType(AuthorityType authorityType);
}