package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.model.dto.AccountDto;
import com.pencilwith.apiserver.model.entity.Account;
import com.pencilwith.apiserver.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private static SignUpRepository signUpRepository;

    public void signUp(AccountDto accountDto) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        signUpRepository.save(account);
    }
}
