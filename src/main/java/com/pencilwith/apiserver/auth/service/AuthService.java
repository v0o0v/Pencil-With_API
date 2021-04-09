package com.pencilwith.apiserver.auth.service;

import com.pencilwith.apiserver.auth.repository.AuthorityRepository;
import com.pencilwith.apiserver.auth.repository.UserRepository;
import com.pencilwith.apiserver.common.enums.AuthorityType;
import com.pencilwith.apiserver.common.jwt.TokenProvider;
import com.pencilwith.apiserver.common.mapper.UserMapper;
import com.pencilwith.apiserver.common.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.common.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.common.model.entity.Authority;
import com.pencilwith.apiserver.common.model.entity.User;
import com.pencilwith.apiserver.common.model.entity.UserAuthority;
import com.pencilwith.apiserver.common.model.request.SignUpRequest;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    protected final LinkedHashMap<String, UserInfoResponseDto> userInfoStorage;

    protected final TokenProvider tokenProvider;

    protected final AuthenticationManagerBuilder authenticationManagerBuilder;

    protected final PasswordEncoder passwordEncoder;

    protected final UserRepository userRepository;

    protected final AuthorityRepository authorityRepository;

    public boolean isUsernameDuplicated(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Transactional
    public AuthenticationDto signUp(SignUpRequest request) {
        request.findIdPassword(userInfoStorage);

        User user = UserMapper.requestToEntity(request, passwordEncoder);
        UserAuthority userAuthority = getUserAuthorityMapping(user);
        user.addAuthority(userAuthority);
        User savedUser = userRepository.save(user);

        return new AuthenticationDto(request.getId(), request.getPassword());
    }

    private UserAuthority getUserAuthorityMapping(User user) {
        Authority authority = authorityRepository.findAuthorityByType(AuthorityType.ROLE_USER);

        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .authority(authority)
                .build();
        return userAuthority;
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
