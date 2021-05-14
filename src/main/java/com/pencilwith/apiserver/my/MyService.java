package com.pencilwith.apiserver.my;


import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

    final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyDTO.UserDTO getUserInfo(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));
        return new MyDTO.UserDTO(user);
    }
}