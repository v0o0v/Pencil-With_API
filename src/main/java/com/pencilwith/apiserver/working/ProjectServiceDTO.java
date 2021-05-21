package com.pencilwith.apiserver.working;

import com.pencilwith.apiserver.domain.entity.*;
import com.pencilwith.apiserver.start.model.enums.CareerType;
import com.pencilwith.apiserver.start.model.enums.GenderType;
import com.pencilwith.apiserver.start.model.enums.LocationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceDTO {

    @Getter
    @Setter
    public static class ProjectTitleDTO {

        private Long projectId;

        private String title;

        public ProjectTitleDTO(Project project) {
            this.projectId = project.getId();
            this.title = project.getTitle();
        }
    }

    @Getter
    @Setter
    public static class MyProjectDTO {

        private List<ProjectTitleDTO> ownerProjects;

        private List<ProjectTitleDTO> crewProjects;

        public MyProjectDTO(User user) {
            this.ownerProjects = user.getOwnerProjectList().stream()
                    .filter(project -> project.getStatus().equals(ProjectStatus.PROGRESS))
                    .map(ProjectTitleDTO::new).collect(Collectors.toList());
            this.crewProjects = user.getProject().stream()
                    .filter(project -> project.getStatus().equals(ProjectStatus.PROGRESS))
                    .map(ProjectTitleDTO::new).collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class ProjectDTO {

        private Long projectId;
        private String ownerId;
        private String title;
        private LocalDateTime createdAt;
        private ProjectStatus status;
        private List<ChapterTitleDto> chapterList;
        private List<CrewDTO> crewList;

        @Builder
        public ProjectDTO(Project project) {
            this.projectId = project.getId();
            this.ownerId = project.getOwner().getId();
            this.title = project.getTitle();
            this.createdAt = project.getCreatedAt();
            this.status = project.getStatus();
            this.chapterList = project.getChapterList().stream().map(ChapterTitleDto::new).collect(Collectors.toList());
            this.crewList = project.getCrewList().stream().map(CrewDTO::new).collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class CrewDTO {

        private String userId;
        private String name;
        private GenderType genderType;
        private LocalDate birth;
        private LocationType locationType;
        private CareerType careerType;
        private String introduction;
        private String profileImage;

        public CrewDTO(User user) {
            this.userId = user.getId();
            this.name = user.getUsername();
            this.genderType = user.getGenderType();
            this.birth = user.getBirth();
            this.locationType = user.getLocationType();
            this.careerType = user.getCareerType();
            this.introduction = user.getIntroduction();
            this.profileImage = user.getProfileImage();
        }
    }


    @Getter
    @Setter
    public static class ChapterTitleDto {

        private Long chapterId;
        private String content;
        private LocalDateTime createAt;
        private ChapterStatus status;
        private String title;

        @Builder
        public ChapterTitleDto(Chapter chapter) {
            this.chapterId = chapter.getId();
            this.createAt = chapter.getCreateAt();
            this.status = chapter.getStatus();
            this.title = chapter.getTitle();
        }
    }


    @Getter
    @Setter
    public static class ChapterDto {

        private Long chapterId;
        private String content;
        private LocalDateTime createAt;
        private ChapterStatus status;
        private String title;

        @Builder
        public ChapterDto(Chapter chapter) {
            this.chapterId = chapter.getId();
            this.content = chapter.getContent();
            this.createAt = chapter.getCreateAt();
            this.status = chapter.getStatus();
            this.title = chapter.getTitle();
        }
    }

}
