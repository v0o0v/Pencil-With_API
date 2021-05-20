package com.pencilwith.apiserver.working;

import com.pencilwith.apiserver.domain.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        private List<ChapterDto> chapterList;
        private ProjectStatus status;

        @Builder
        public ProjectDTO(Project project) {
            this.projectId = project.getId();
            this.ownerId = project.getOwner().getId();
            this.title = project.getTitle();
            this.createdAt = project.getCreatedAt();
            this.chapterList = project.getChapterList().stream().map(ChapterDto::new).collect(Collectors.toList());
            this.status = project.getStatus();
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
