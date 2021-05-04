package com.pencilwith.apiserver.crew;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @ApiOperation(
            value = "크루 모집 생성"
            , notes = "크루 모집 요강 정보를 바탕으로 크루 모집을 생성합니다."
    )
    @PostMapping("/recruitment")
    public ResponseEntity<?> recruitCrew(@RequestBody @Validated CrewControllerDTO.RecruitRequestDTO request) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.makeRecruit(request));
    }

    @ApiOperation(
            value = "크루 모집 단건 조회"
            , notes = "크루 모집을 단건으로 조회합니다."
    )
    @GetMapping("/recruitment/{id}")
    public ResponseEntity<?> getCrewRecruits(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.getRecruit(id));
    }

    @ApiOperation(
            value = "크루 모집 페이징 조회"
            , notes = "크루 모집을 조회합니다. 페이징과 필터링을 지원합니다. 생성 최신순으로 기본 정렬되어 반환합니다."

    )
    @GetMapping("/recruitment")
    public ResponseEntity<?> getCrewRecruits( Pageable pageable) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.getRecruits(pageable));
    }

    @ApiOperation(
            value = "크루 모집 유저 참여"
            , notes = "크루 모집에 유저를 참여시킵니다."
    )
    @PostMapping("/recruitment/{id}/join")
    public ResponseEntity<?> joinRecruit(@PathVariable Long id, @RequestParam String userId) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.joinRecruit(id, userId));
    }

}
