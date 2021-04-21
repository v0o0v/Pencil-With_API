package com.pencilwith.apiserver.crew;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class CrewControllerDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitRequestDTO {

        @NotNull
        private String ownerId;

        @NotNull
        private String title;

        @NotNull
        private Long projectId;

        @Min(1)
        private int maxNumber;

        private LocalDate startDate;

        private LocalDate endDate;

        private String content;

        private List<String> userIdListToNoti;

        private NovelGenre genre;

    }


}
