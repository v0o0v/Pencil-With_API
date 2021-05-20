package com.pencilwith.apiserver.working;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceDTO {

    @Getter
    @Setter
    public static class ProjectDTO {

        private Long id;

        private String title;

        public ProjectDTO(Project project) {
            this.id = project.getId();
            this.title = project.getTitle();
        }
    }

    @Getter
    @Setter
    public static class MyProjectDTO {

        private List<ProjectDTO> ownerProjects;

        private List<ProjectDTO> crewProjects;

        public MyProjectDTO(User user) {
            this.ownerProjects = user.getOwnerProjectList().stream().map(ProjectDTO::new).collect(Collectors.toList());
            this.crewProjects = user.getProject().stream().map(ProjectDTO::new).collect(Collectors.toList());
        }
    }


}
