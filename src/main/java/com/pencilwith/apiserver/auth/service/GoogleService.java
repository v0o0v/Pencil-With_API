package com.pencilwith.apiserver.auth.service;

import com.pencilwith.apiserver.common.model.dto.AuthenticationResultDto;
import org.springframework.stereotype.Service;

@Service
public class GoogleService extends AuthService {

    public AuthenticationResultDto processAuthentication(String authorizationCode) {
        return null;
    }
}
