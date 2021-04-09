package com.pencilwith.apiserver.auth.service;

import com.pencilwith.apiserver.auth.repository.AuthorityRepository;
import com.pencilwith.apiserver.auth.repository.UserRepository;
import com.pencilwith.apiserver.common.jwt.TokenProvider;
import com.pencilwith.apiserver.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.common.model.dto.UserInfoResponseDto;
import java.util.LinkedHashMap;
import javax.transaction.Transactional;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GoogleService extends AuthService {

    public GoogleService(LinkedHashMap<String, UserInfoResponseDto> userInfoStorage,
                         TokenProvider tokenProvider,
                         AuthenticationManagerBuilder authenticationManagerBuilder,
                         PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         AuthorityRepository authorityRepository) {
        super(userInfoStorage, tokenProvider, authenticationManagerBuilder, passwordEncoder, userRepository, authorityRepository);
    }

    public AuthenticationResultDto processAuthentication(String authorizationCode) {
        return null;
    }
}
