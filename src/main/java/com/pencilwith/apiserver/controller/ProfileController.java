package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.UserInfo;
import com.pencilwith.apiserver.model.entity.UserInfoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo findUserInfo(@PathVariable Long id) {
        UserInfo userInfo = new UserInfo();

        return userInfo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo createUserInfo(@RequestParam("img") MultipartFile file, @RequestBody UserInfoRequest userInfo) {
        /* 작업 처리 */
        UserInfo newInfo = new UserInfo();

        return newInfo;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo updateUserInfo(@PathVariable Long id, @RequestParam("img") MultipartFile file, @RequestBody UserInfoRequest userInfo) {
        /* 수정 작업 */
        UserInfo updateInfo = new UserInfo();

        return updateInfo;
    }
}
