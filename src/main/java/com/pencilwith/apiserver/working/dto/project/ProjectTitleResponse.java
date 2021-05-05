package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProjectTitleResponse {
    private String ownerId;
    private Long projectId;
    private String title;

    @Builder
    public ProjectTitleResponse(String ownerId, Long projectId, String title) {
        this.ownerId = ownerId;
        this.projectId = projectId;
        this.title = title;
    }
}
