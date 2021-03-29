package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.dto.AccountDto;
import com.pencilwith.apiserver.service.SignUpService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private static SignUpService signUpService;

    @GetMapping
    public void test() {
        System.out.println("test");
    }

    @PostMapping
    public void signUp(@Valid AccountDto accountDto) {
        signUpService.signUp(accountDto);
    }
}
