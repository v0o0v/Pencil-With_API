package com.pencilwith.apiserver.start.controller;

import com.pencilwith.apiserver.start.config.response.ApiResponse;
import com.pencilwith.apiserver.start.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.start.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.start.model.enums.LoginType;
import com.pencilwith.apiserver.start.model.request.SignUpRequest;
import com.pencilwith.apiserver.start.service.AuthService;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/duplication/{username}")
    public ApiResponse<Boolean> isNicknameDuplicated(@PathVariable @Size(max = 10) String username) {
        return new ApiResponse<>(authService.isUsernameDuplicated(username));
    }

    @PostMapping("/sign-up")
    public ApiResponse<AuthenticationResultDto> processSignUp(@RequestBody @NotNull SignUpRequest request) {
        AuthenticationDto dto = authService.signUp(request);
        return new ApiResponse<>(authService.makeJwtToken(dto));
    }

    @PostMapping("/kakao/authentication")
    public ApiResponse<AuthenticationResultDto> processKakaoAuthentication(@RequestBody @NotEmpty String accessToken) {
        return new ApiResponse<>(authService.processAuthentication(accessToken, LoginType.KAKAO));
    }

    @PostMapping("/google/authentication")
    public ApiResponse<AuthenticationResultDto> processGoogleAuthentication(@RequestBody @NotEmpty String accessToken) {
        return new ApiResponse<>(authService.processAuthentication(accessToken, LoginType.GOOGLE));
    }
}
