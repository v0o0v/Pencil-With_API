package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.NovelGenre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public class CrewControllerDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitRequestDTO {

        @NotNull
        private String title;

        @NotNull
        private Long projectId;

        @Min(value = 1, message = "모집인원은 최소 1명 이상입니다.")
        private int maxNumber;

        private LocalDate startDate;

        private LocalDate endDate;

        private String content;

        @Size(min = 1, message = "최소 하나 이상의 장르를 설정해야 합니다.")
        private Set<NovelGenre> genre;

    }

}
