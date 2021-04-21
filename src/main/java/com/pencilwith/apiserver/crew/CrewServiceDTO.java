package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CrewServiceDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CrewRecruitDTO {

        private String title;

        private Long projectId;

        private Integer maxNumber;

        private LocalDate startDate;

        private LocalDate endDate;

        private String content;

        private List<String> userIdListToNoti;

        private NovelGenre genre;

        public CrewRecruitDTO(CrewRecruit crewRecruit) {
            this.title = crewRecruit.getTitle();
            this.projectId = crewRecruit.getProject().getId();
            this.maxNumber = crewRecruit.getMaxNumber();
            this.startDate = crewRecruit.getStartDate();
            this.endDate = crewRecruit.getEndDate();
            this.content = crewRecruit.getContent();
            this.userIdListToNoti = crewRecruit.getNotiUserList().stream().map(User::getId).collect(Collectors.toList());
            this.genre = crewRecruit.getGenre();
        }
    }

}
