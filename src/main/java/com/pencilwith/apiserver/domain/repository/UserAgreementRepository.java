package com.pencilwith.apiserver.domain.repository;

import com.pencilwith.apiserver.domain.entity.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {

    UserAgreement findFirstByOrderByIdDesc();
}
