package com.pencilwith.apiserver.working;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;

    @ApiOperation(value = "나의 진행중인 프로젝트 리스트 조회",
            notes = "나의 프로젝트 리스트와 Crew로 참여중인 프로젝트 리스트를 반환합니다. 진행중인 프로젝트만 반환합니다.")
    @GetMapping("/my")
    public ResponseEntity<?> getMyProjectList() {
        return ResponseEntity.ok(projectService.getMyProjectList());
    }

    @ApiOperation(value = "Project 한건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @ApiOperation(value = "Project 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "신규 Project 생성")
    @PostMapping
    public ResponseEntity<?> createProject(@Validated @RequestBody ProjectControllerRequestDTO.ProjectCreateRequestDTO dto) {
        return ResponseEntity.ok(projectService.createProject(dto.getTitle()));
    }

    @ApiOperation(value = "Project 집필 완료 처리")
    @PutMapping("/{id}/finish")
    public ResponseEntity<?> finishProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.finishProject(id));
    }

    @ApiOperation(value = "신규 Chapter 생성")
    @PostMapping("/{id}/chapter")
    public ResponseEntity<?> createChapter(@PathVariable Long id
            , @Validated @RequestBody ProjectControllerRequestDTO.ChapterCreateRequestDTO dto) {
        return ResponseEntity.ok(projectService.createChapter(id, dto.getTitle()));
    }

    @ApiOperation(value = "Chapter 배포")
    @PutMapping("/{projectId}/chapter/{chapterId}/publish")
    public ResponseEntity<?> publishChapter(
            @PathVariable Long projectId
            , @PathVariable Long chapterId) {
        return ResponseEntity.ok(projectService.publishChapter(projectId, chapterId));
    }

    @ApiOperation(value = "Chapter 컨텐트 수정")
    @PutMapping("/{projectId}/chapter/{chapterId}/content")
    public ResponseEntity<?> modifyChapterContent(
            @PathVariable Long projectId
            , @PathVariable Long chapterId
            , @Validated @RequestBody ProjectControllerRequestDTO.ChapterContentModifyRequestDTO dto
    ) {
        return ResponseEntity.ok(projectService.modifyChapterContent(projectId, chapterId, dto.getContent()));
    }

    @ApiOperation(value = "신규 Feedback 추가")
    @PostMapping("/{id}/feedback")
    public ResponseEntity<?> createFeedback(
            @PathVariable Long id
            , @RequestParam String content
            , @RequestParam String position
            , @RequestParam(required = false) MultipartFile soundFile
    ) {
        return ResponseEntity.ok(projectService.createFeedback(id, content, position, soundFile));
    }

    @ApiOperation(value = "Feedback 리스트 조회", notes = "페이징 형식으로 반환합니다. 정렬은 최신순입니다.")
    @GetMapping("/{id}/feedback")
    public ResponseEntity<?> getFeedbackList(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(projectService.getFeedbackList(id, pageable));
    }

    @ApiOperation(value = "Feedback 단 건 조회")
    @GetMapping("/{projectId}/feedback/{feedbackId}")
    public ResponseEntity<?> getFeedback(@PathVariable Long projectId, @PathVariable Long feedbackId) {
        return ResponseEntity.ok(projectService.getFeedback(projectId, feedbackId));
    }

    @ApiOperation(value = "Feedback 수정", notes = "특정 속성 값이 null일 경우 수정하지 않습니다.")
    @PutMapping("/{projectId}/feedback/{feedbackId}")
    public ResponseEntity<?> modifyFeedback(
            @PathVariable Long projectId
            , @PathVariable Long feedbackId
            , @Validated @RequestBody ProjectControllerRequestDTO.FeedbackModifyRequestDTO dto
    ) {
        return ResponseEntity.ok(projectService.modifyFeedback(projectId, feedbackId, dto.getContent(), dto.getPosition()));
    }

    @ApiOperation(value = "Feedback 삭제")
    @DeleteMapping("/{projectId}/feedback/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable Long projectId
            , @PathVariable Long feedbackId
    ) {
        projectService.deleteFeedback(projectId, feedbackId);
        return ResponseEntity.ok(null);
    }

    //reply 추가

    //reply 삭제



    //Crew 제외

    //Crew 조인

}