package com.pencilwith.apiserver.start.user.service;

import com.pencilwith.apiserver.start.common.model.dto.UserDto;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.start.common.mapper.UserMapper;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserInfo(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return null;
        }

        return UserMapper.entityToDto(optionalUser.get());
    }
}
