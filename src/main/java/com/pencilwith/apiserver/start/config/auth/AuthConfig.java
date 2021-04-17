package com.pencilwith.apiserver.start.config.auth;

import com.pencilwith.apiserver.start.model.dto.UserInfoResponseDto;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    private static final int MAX_SIZE = 1000;

    // TODO: 스레드 세이프하도록 변경 필요
    @Bean
    public LinkedHashMap<String, UserInfoResponseDto> userInfoStorage() {
        return new LinkedHashMap<>(MAX_SIZE * 10 / 7, 0.7f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, UserInfoResponseDto> eldest) {
                return size() > MAX_SIZE;
            }
        };
    }
}
