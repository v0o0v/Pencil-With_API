package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.model.dto.AuthenticationResultDto;
import org.springframework.stereotype.Service;

@Service
public class GoogleService extends AuthService {

    public AuthenticationResultDto processAuthentication(String authorizationCode) {
        return null;
    }
}
