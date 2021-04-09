package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.dto.UserDto;
import com.pencilwith.apiserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserInfo(username), HttpStatus.OK);
    }
}
