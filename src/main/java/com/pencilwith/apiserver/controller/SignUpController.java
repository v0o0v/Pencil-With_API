package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.dto.UserDto;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import com.pencilwith.apiserver.service.SignUpService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping("/nickname/duplication")
    public ResponseEntity<Boolean> isNicknameDuplicated(@Size(max = 10) @NotBlank String nickname) {
        return new ResponseEntity<>(signUpService.isNickNameDuplicated(nickname), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return new ResponseEntity<>(signUpService.signUp(signUpRequest), HttpStatus.OK);
    }
}
