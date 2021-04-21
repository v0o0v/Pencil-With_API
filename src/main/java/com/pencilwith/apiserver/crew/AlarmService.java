package com.pencilwith.apiserver.crew;


import com.pencilwith.apiserver.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    public void makeAlarm(User from, List<User> to, String msg) {

    }
}
