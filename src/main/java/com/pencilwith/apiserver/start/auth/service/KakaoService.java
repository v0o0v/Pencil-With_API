package com.pencilwith.apiserver.start.auth.service;

import com.pencilwith.apiserver.domain.repository.AuthorityRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import com.pencilwith.apiserver.start.common.enums.LoginType;
import com.pencilwith.apiserver.start.common.jwt.TokenProvider;
import com.pencilwith.apiserver.start.common.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.start.common.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.start.common.model.dto.KakaoTokenResponseDto;
import com.pencilwith.apiserver.start.common.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.domain.entity.User;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.transaction.Transactional;

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
@Transactional
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

    public KakaoService(LinkedHashMap<String, UserInfoResponseDto> userInfoStorage,
                        TokenProvider tokenProvider,
                        AuthenticationManagerBuilder authenticationManagerBuilder,
                        PasswordEncoder passwordEncoder,
                        UserRepository userRepository,
                        AuthorityRepository authorityRepository) {
        super(userInfoStorage, tokenProvider, authenticationManagerBuilder, passwordEncoder, userRepository, authorityRepository);
    }

    public AuthenticationResultDto processAuthentication(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        UserInfoResponseDto userInfo = getUserInfo(accessToken, LoginType.KAKAO);
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

        KakaoTokenResponseDto responseDto = restTemplate.postForObject(tokenRequestUri, requestParams, KakaoTokenResponseDto.class);

        return responseDto.getAccess_token();
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
