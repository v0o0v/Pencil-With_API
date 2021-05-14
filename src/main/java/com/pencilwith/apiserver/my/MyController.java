package com.pencilwith.apiserver.my;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @ApiOperation(value = "유저 프로필 단건 조회")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.getUserInfo(id));
    }

    @ApiOperation(value = "유저 프로필 수정"
            , notes = "요청한 값으로 유저 프로필 값을 변경합니다. 요청 속성이 null이면 수정하지 않습니다.")
    @PutMapping("/user/{id}")
    public ResponseEntity<?> modifyUserInfo(
            @PathVariable String id
            , @RequestBody MyDTO.ModifyUserDTO modifyUserDTO) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.modifyUserInfo(id, modifyUserDTO));
    }

}
