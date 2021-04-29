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
            value = "크루 모집 조회"
            , notes = "크루 모집을 조회합니다. 페이징과 정렬을 지원합니다.\n" +
            "정렬 컬럼 : 저자 생일(owner.birth) / 저자 경력(owner.careerType) / 저자 성별(owner.genderType)"
    )
    @GetMapping("/recruitment")
    public ResponseEntity<?> getCrewRecruits(Pageable pageable) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.getRecruits(pageable));
    }

}
