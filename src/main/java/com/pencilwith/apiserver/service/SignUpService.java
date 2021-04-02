package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.model.dto.UserDto;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public boolean isNickNameDuplicated(String nickname) {
        return userRepository.findUserByNickname(nickname) != null;
    }

    public UserDto signUp(SignUpRequest signUpRequest) {
        User user = UserMapper.requestToEntity(signUpRequest, passwordEncoder);

        Authority authority = authorityRepository.findAuthorityByType(AuthorityType.ROLE_USER);

        UserAuthorityMapping userAuthorityMapping = UserAuthorityMapping.builder()
                .user(user)
                .authority(authority)
                .build();

        user.addAuthority(userAuthorityMapping);

        User savedUser = userRepository.save(user);

        return UserMapper.entityToDto(savedUser);
    }
}
