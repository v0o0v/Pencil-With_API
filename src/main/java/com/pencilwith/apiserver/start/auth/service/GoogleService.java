package com.pencilwith.apiserver.start.auth.service;

import com.pencilwith.apiserver.start.auth.repository.AuthorityRepository;
import com.pencilwith.apiserver.start.auth.repository.UserRepository;
import com.pencilwith.apiserver.start.common.enums.LoginType;
import com.pencilwith.apiserver.start.common.jwt.TokenProvider;
import com.pencilwith.apiserver.start.common.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.start.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.start.common.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.domain.entity.User;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class GoogleService extends AuthService {

    @Value("${spring.security.oauth2.client.registration.google.userinfo-request-uri}")
    protected String userInfoRequestUri;

    public GoogleService(LinkedHashMap<String, UserInfoResponseDto> userInfoStorage,
                         TokenProvider tokenProvider,
                         AuthenticationManagerBuilder authenticationManagerBuilder,
                         PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         AuthorityRepository authorityRepository) {
        super(userInfoStorage, tokenProvider, authenticationManagerBuilder, passwordEncoder, userRepository, authorityRepository);
    }

    public AuthenticationResultDto processAuthentication(String accessToken) {
        UserInfoResponseDto userInfo = getUserInfo(accessToken, LoginType.GOOGLE);
        Optional<User> optionalUser = isUserRegistered(userInfo.getLoginType() + userInfo.getUserId());

        if (optionalUser.isEmpty()) {
            userInfoStorage.put(accessToken, userInfo);
            return AuthenticationResultDto.builder()
                    .isRegistered(false)
                    .authorizationCode(accessToken)
                    // TODO: 프로필 이미지 URI 정보 추가 전달 필요
                    .build();
        }

        return makeJwtToken(new AuthenticationDto(userInfo.getLoginType() + userInfo.getUserId(), userInfo.getUserId()));
    }

    private UserInfoResponseDto getUserInfo(String accessToken, LoginType loginType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity entity = new HttpEntity(headers);

        UserInfoResponseDto userInfo = restTemplate.exchange(userInfoRequestUri, HttpMethod.GET, entity, UserInfoResponseDto.class).getBody();
        userInfo.setLoginType(loginType.getType());

        return userInfo;
    }
}
