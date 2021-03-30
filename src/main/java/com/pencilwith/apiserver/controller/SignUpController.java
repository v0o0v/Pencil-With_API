package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.request.SignUpRequest;
import com.pencilwith.apiserver.service.SignUpService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return new ResponseEntity<>(signUpService.signUp(signUpRequest), HttpStatus.OK);
    }
}
