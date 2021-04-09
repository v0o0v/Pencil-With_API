package com.pencilwith.apiserver.auth.controller;

import com.pencilwith.apiserver.common.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.common.model.request.SignUpRequest;
import com.pencilwith.apiserver.auth.service.AuthService;
import com.pencilwith.apiserver.auth.service.GoogleService;
import com.pencilwith.apiserver.auth.service.KakaoService;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final KakaoService kakaoService;

    private final GoogleService googleService;

    @GetMapping("/duplication/{username}")
    public ResponseEntity<Boolean> isNicknameDuplicated(@PathVariable @Size(max = 10) String username) {
        return new ResponseEntity<>(authService.isUsernameDuplicated(username), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResultDto> processSignUp(@RequestBody @NotNull SignUpRequest request) {
        AuthenticationDto dto = authService.signUp(request);
        return new ResponseEntity<>(authService.makeJwtToken(dto), HttpStatus.OK);
    }

    @PostMapping("/kakao/authentication")
    public ResponseEntity<AuthenticationResultDto> processKakaoAuthentication(@RequestBody @NotEmpty String authorizationCode) {
        return new ResponseEntity<>(kakaoService.processAuthentication(authorizationCode), HttpStatus.OK);
    }

    @PostMapping("/google/authentication")
    public ResponseEntity<AuthenticationResultDto> processGoogleAuthentication(@RequestBody @NotEmpty String authorizationCode) {
        return new ResponseEntity<>(googleService.processAuthentication(authorizationCode), HttpStatus.OK);
    }
}
