package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.Authority;
import com.pencilwith.apiserver.domain.entity.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findAuthorityByType(AuthorityType authorityType);
}
