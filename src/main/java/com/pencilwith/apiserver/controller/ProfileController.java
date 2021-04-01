package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.Account;
import com.pencilwith.apiserver.model.entity.AccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findUserInfo(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Account userInfo = new Account();

        return userInfo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createUserInfo(@RequestParam("img") MultipartFile file, @RequestBody AccountRequest userInfo) {
        /* 작업 처리 */
        Account newInfo = new Account();

        return newInfo;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateUserInfo(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam("img") MultipartFile file, @RequestBody AccountRequest userInfo) {
        /* 수정 작업 */
        Account updateInfo = new Account();
        return updateInfo;
    }
}
