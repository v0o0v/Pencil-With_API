package com.pencilwith.apiserver.start.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pencilwith.apiserver.domain.entity.Authority;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.entity.UserAgreement;
import com.pencilwith.apiserver.domain.entity.UserAuthority;
import com.pencilwith.apiserver.domain.exception.InternalServerErrorException;
import com.pencilwith.apiserver.domain.repository.AuthorityRepository;
import com.pencilwith.apiserver.domain.repository.UserAgreementRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import com.pencilwith.apiserver.start.config.jwt.TokenProvider;
import com.pencilwith.apiserver.start.model.dto.AuthenticationDto;
import com.pencilwith.apiserver.start.model.dto.AuthenticationResultDto;
import com.pencilwith.apiserver.start.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.domain.entity.AuthorityType;
import com.pencilwith.apiserver.start.model.enums.LoginType;
import com.pencilwith.apiserver.start.model.mapper.UserMapper;
import com.pencilwith.apiserver.start.model.request.SignUpRequest;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final LinkedHashMap<String, UserInfoResponseDto> userInfoStorage;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final UserAgreementRepository userAgreementRepository;

    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.client.registration.google.userinfo-endpoint}")
    protected String googleUserInfoEndpoint;

    @Value("${spring.security.oauth2.client.registration.kakao.userinfo-endpoint}")
    protected String kakaoUserInfoEndpoint;

    public boolean isUsernameDuplicated(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public AuthenticationDto signUp(SignUpRequest request) {
        request.findIdPassword(userInfoStorage);

        User user = UserMapper.requestToEntity(request, passwordEncoder);
        UserAuthority userAuthority = getUserAuthority(user);
        user.addAuthority(userAuthority);
        userRepository.save(user);

        return new AuthenticationDto(request.getId(), request.getPassword());
    }

    private UserAuthority getUserAuthority(User user) {
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
                .jwtToken("Bearer " + jwtToken)
                .userId(dto.getUserId())
                .build();
    }

    public AuthenticationResultDto processAuthentication(String accessToken, LoginType loginType) {
        UserInfoResponseDto userInfo = getUserInfo(accessToken, loginType);
        Optional<User> optionalUser = isUserRegistered(userInfo.getLoginType() + userInfo.getUserId());

        if (optionalUser.isEmpty()) {
            return preProcessForSignUp(accessToken, userInfo);
        }

        return makeJwtToken(new AuthenticationDto(userInfo.getLoginType() + userInfo.getUserId(), userInfo.getUserId()));
    }

    private AuthenticationResultDto preProcessForSignUp(String accessToken, UserInfoResponseDto userInfo) {
        // 회원가입 요청 시 accessToken을 다시 받아서 정상적인 요청인지를 검증하기 위해 임시로 accessToken 저장
        userInfoStorage.put(accessToken, userInfo);

        UserAgreement latestUserAgreement = userAgreementRepository.findFirstByOrderByIdDesc();

        return AuthenticationResultDto.builder()
                .isRegistered(false)
                .accessToken(accessToken)
                .userAgreement(latestUserAgreement)                
                .build();
    }

    private UserInfoResponseDto getUserInfo(String accessToken, LoginType loginType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity entity = new HttpEntity(headers);

        String userInfoEndPoint = loginType == LoginType.GOOGLE ? googleUserInfoEndpoint : kakaoUserInfoEndpoint;

        UserInfoResponseDto userInfo = restTemplate.exchange(userInfoEndPoint, HttpMethod.GET, entity, UserInfoResponseDto.class).getBody();
        userInfo.setLoginType(loginType.getType());

        String body = restTemplate.exchange(userInfoEndPoint, HttpMethod.GET, entity, String.class).getBody();
        JsonNode node = null;
        try {
            node = this.objectMapper.readTree(body);
            switch (loginType) {
                case KAKAO:
                    userInfo.setProfileImage(node.get("kakao_account").get("profile").get("profile_image_url").asText());
                    break;
                case GOOGLE:
                    userInfo.setProfileImage(node.get("picture").asText());
                    break;
            }
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("토큰을 사용하여 사용자 정보를 불러올 수 없습니다.");
        }

        return userInfo;
    }
}
