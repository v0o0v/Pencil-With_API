package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.model.entity.Account;
import com.pencilwith.apiserver.model.mapper.AccountMapper;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import com.pencilwith.apiserver.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final SignUpRepository signUpRepository;

    public long signUp(SignUpRequest signUpRequest) {
        Account account = AccountMapper.requestToEntity(signUpRequest);
        Account savedAccount = signUpRepository.save(account);
        return savedAccount.getId();
    }
}
