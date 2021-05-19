package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProjectTitleResponse {
    private Long projectId;
    private String title;

    @Builder
    public ProjectTitleResponse(Long projectId, String title) {
        this.projectId = projectId;
        this.title = title;
    }
}
