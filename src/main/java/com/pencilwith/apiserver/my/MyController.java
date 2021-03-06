package com.pencilwith.apiserver.my;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation(value = "유저 탈퇴"
            , notes = "유저 데이터 및 관련 프로젝트/ 피드백/ 리플/ 크루/ 크루모집 모두 삭제됩니다.")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        myService.deleteUser(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

    @ApiOperation(value = "유저 프로필 사진 수정")
    @PutMapping("/user/{id}/profileImage")
    public ResponseEntity<?> modifyUserProfileImage(@PathVariable String id, @RequestParam MultipartFile image) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.modifyUserProfileImage(id, image));
    }

    @ApiOperation(value = "완료된 프로젝트 리스트 조회")
    @GetMapping("/user/{id}/finishedProjects")
    public ResponseEntity<?> getFinishedProjects(@PathVariable String id) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.getFinishedProjects(id));
    }

    @ApiOperation(value = "완료된 프로젝트 재작업"
            , notes = "반환값은 해당 유저의 완료된 프로젝트 리스트")
    @PutMapping("/user/{userId}/reworkProject/{projectId}")
    public ResponseEntity<?> reworkProject(@PathVariable String userId, @PathVariable Long projectId) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.reworkProject(userId, projectId));
    }

}
