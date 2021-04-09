package com.pencilwith.apiserver.auth.service;

import com.pencilwith.apiserver.auth.repository.AuthorityRepository;
import com.pencilwith.apiserver.auth.repository.UserRepository;
import com.pencilwith.apiserver.common.jwt.TokenProvider;
import com.pencilwith.apiserver.common.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.common.model.dto.KakaoTokenResponseDto;
import com.pencilwith.apiserver.common.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.common.model.entity.User;
import java.util.LinkedHashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService extends AuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    protected String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    protected String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    protected String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.token-request-uri}")
    protected String tokenRequestUri;

    @Value("${spring.security.oauth2.client.registration.kakao.userinfo-request-uri}")
    protected String userInfoRequestUri;

    public KakaoService(
            LinkedHashMap<String, UserInfoResponseDto> userInfoStorage,
            TokenProvider tokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            AuthorityRepository authorityRepository) {
        super(userInfoStorage, tokenProvider, authenticationManagerBuilder, passwordEncoder,
                userRepository, authorityRepository);
    }

    public AuthenticationResultDto processAuthentication(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        UserInfoResponseDto userInfo = getUserInfo(accessToken, "kakao");
        Optional<User> optionalUser = isUserRegistered(userInfo.getLoginType() + userInfo.getUserId());

        if (optionalUser.isEmpty()) {
            userInfoStorage.put(authorizationCode, userInfo);
            return AuthenticationResultDto.builder()
                    .isRegistered(false)
                    .authorizationCode(authorizationCode)
                    .build();
        }

        return makeJwtToken(new AuthenticationDto(userInfo.getLoginType() + userInfo.getUserId(), userInfo.getUserId()));
    }

    private String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);
        map.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> requestParams = new HttpEntity<>(map, headers);

        KakaoTokenResponseDto responseDto = restTemplate
                .postForObject(tokenRequestUri, requestParams, KakaoTokenResponseDto.class);
        return responseDto.getAccess_token();
    }

    public UserInfoResponseDto getUserInfo(String accessToken, String loginType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity entity = new HttpEntity(headers);

        UserInfoResponseDto responseDto = restTemplate.exchange(userInfoRequestUri, HttpMethod.GET, entity, UserInfoResponseDto.class).getBody();
        responseDto.setLoginType(loginType);

        return responseDto;
    }
}
