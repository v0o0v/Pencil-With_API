package com.pencilwith.apiserver.repository;

import com.pencilwith.apiserver.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<Account, Long> {

    Account findAccountByNickName(String nickName);
}
