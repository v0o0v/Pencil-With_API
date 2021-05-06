package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.NovelGenre;
import com.pencilwith.apiserver.start.model.enums.CareerType;
import com.pencilwith.apiserver.start.model.enums.GenderType;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseEntity<?> recruitCrew(@RequestBody @Validated CrewControllerDTO.PostRecruitRequestDTO request) {

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
            , notes = "크루 모집을 조회합니다. 게시중인 크루 모집만 조회합니다. 페이징과 필터링을 지원하며 생성 최신순으로 기본 정렬되어 반환합니다.\n" +
            "필터링을 원하는 속성에 값을 넣으면 해당 필터가 존재하는 데이터를 반환하며 아무것도 넣지 않으면 해당 속성은 필터링하지 않습니다."

    )
    @GetMapping("/recruitment")
    public ResponseEntity<?> getCrewRecruits(
            @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size
            , @RequestParam(required = false) Set<GenderType> genderTypes
            , @RequestParam(required = false) Integer minAge
            , @RequestParam(required = false) Integer maxAge
            , @RequestParam(required = false) Set<CareerType> careerTypes
            , @RequestParam(required = false) Set<NovelGenre> novelGenres) {

        CrewServiceDTO.CrewRecruitFilterDTO requestDTO = CrewServiceDTO.CrewRecruitFilterDTO.builder()
                .genderTypes(genderTypes)
                .minAge(minAge)
                .maxAge(maxAge)
                .careerTypes(careerTypes)
                .novelGenres(novelGenres)
                .build();

        if(page==null) page = 0;
        if(size==null) size = 20;

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.getRecruits(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")), requestDTO));
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

    @ApiOperation(
            value = "로그인 유저의 크루 모집 조회"
            , notes = "로그인 유저의 모든 크루 모집을 조회합니다(삭제된 모집 제외). 생성역순으로 정렬되어 반환됩니다."
    )
    @GetMapping("/recruitment/me")
    public ResponseEntity<?> getCrewRecruitsOfMe() {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(crewService.getRecruitOfMe());
    }

}
