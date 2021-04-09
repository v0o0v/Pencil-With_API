package com.pencilwith.apiserver.service;

import com.pencilwith.apiserver.common.jwt.TokenProvider;
import com.pencilwith.apiserver.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.model.entity.Authority;
import com.pencilwith.apiserver.model.entity.User;
import com.pencilwith.apiserver.model.entity.UserAuthorityMapping;
import com.pencilwith.apiserver.model.enums.AuthorityType;
import com.pencilwith.apiserver.model.mapper.UserMapper;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import com.pencilwith.apiserver.repository.AuthorityRepository;
import com.pencilwith.apiserver.repository.UserRepository;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    protected LinkedHashMap<String, UserInfoResponseDto> userInfoStorage;

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AuthorityRepository authorityRepository;

    public boolean isUsernameDuplicated(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Transactional
    public AuthenticationDto signUp(SignUpRequest request) {
        request.findIdPassword(userInfoStorage);

        User user = UserMapper.requestToEntity(request, passwordEncoder);
        UserAuthorityMapping userAuthorityMapping = getUserAuthorityMapping(user);
        user.addAuthority(userAuthorityMapping);
        User savedUser = userRepository.save(user);

        return new AuthenticationDto(request.getId(), request.getPassword());
    }

    private UserAuthorityMapping getUserAuthorityMapping(User user) {
        Authority authority = authorityRepository.findAuthorityByType(AuthorityType.ROLE_USER);

        UserAuthorityMapping userAuthorityMapping = UserAuthorityMapping.builder()
                .user(user)
                .authority(authority)
                .build();
        return userAuthorityMapping;
    }

    public  Optional<User> isUserRegistered(String userId) {
        return userRepository.findById(userId);
    }

    public AuthenticationResultDto makeJwtToken(AuthenticationDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.createToken(authentication);

        return AuthenticationResultDto.builder()
                .isRegistered(true)
                .token("Bearer " + jwtToken)
                .build();
    }
}
