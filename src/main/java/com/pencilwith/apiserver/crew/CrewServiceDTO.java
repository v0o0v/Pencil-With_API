package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.*;
import com.pencilwith.apiserver.domain.entity.CareerType;
import com.pencilwith.apiserver.domain.entity.GenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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

        private Set<NovelGenre> genre;

        private String owner;

        private CrewRecruitState state;

        public CrewRecruitDTO(CrewRecruit crewRecruit) {
            this.title = crewRecruit.getTitle();
            this.projectId = crewRecruit.getProject().getId();
            this.maxNumber = crewRecruit.getMaxNumber();
            this.startDate = crewRecruit.getStartDate();
            this.endDate = crewRecruit.getEndDate();
            this.content = crewRecruit.getContent();
            this.genre = crewRecruit.getGenre();
            this.owner = crewRecruit.getOwner().getId();
            this.state = crewRecruit.getState();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectDTO {

        private Long projectId;

        private List<String> crewList;

        public ProjectDTO(Project project) {
            this.projectId = project.getId();
            this.crewList = project.getCrewList().stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CrewRecruitFilterDTO {

        private Set<GenderType> genderTypes;

        private Integer minAge;

        private Integer maxAge;

        private Set<CareerType> careerTypes;

        private Set<NovelGenre> novelGenres;

        @Builder
        public CrewRecruitFilterDTO(Set<GenderType> genderTypes, Integer minAge, Integer maxAge, Set<CareerType> careerTypes, Set<NovelGenre> novelGenres) {
            this.genderTypes = genderTypes;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.careerTypes = careerTypes;
            this.novelGenres = novelGenres;
        }
    }


}
