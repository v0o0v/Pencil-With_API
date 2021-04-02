package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.model.entity.Authority;
import com.pencilwith.apiserver.model.entity.User;
import com.pencilwith.apiserver.model.entity.UserAuthorityMapping;
import com.pencilwith.apiserver.model.enums.AuthorityType;
import com.pencilwith.apiserver.model.mapper.UserMapper;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import com.pencilwith.apiserver.repository.AuthorityRepository;
import com.pencilwith.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public boolean isNickNameDuplicated(String nickName) {
        return userRepository.findUserByNickName(nickName) != null;
    }

    public User signUp(SignUpRequest signUpRequest) {
        User user = UserMapper.requestToEntity(signUpRequest, passwordEncoder);

        Authority authority = authorityRepository.findAuthorityByType(AuthorityType.ROLE_USER);
        UserAuthorityMapping userAuthorityMapping = UserAuthorityMapping.builder()
                .user(user)
                .authority(authority)
                .build();

        user.addAuthority(userAuthorityMapping);

        return userRepository.save(user);
    }
}
