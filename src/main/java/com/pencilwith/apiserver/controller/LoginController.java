package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.dto.JwtTokenDto;
import com.pencilwith.apiserver.model.request.LoginRequest;
import com.pencilwith.apiserver.service.LoginService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Valid LoginRequest request) {
        return new ResponseEntity<>(loginService.login(request), HttpStatus.OK);
    }
}
