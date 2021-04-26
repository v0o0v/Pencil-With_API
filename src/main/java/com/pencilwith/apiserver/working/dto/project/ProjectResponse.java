package com.pencilwith.apiserver.working.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProjectResponse {
    private String ownerId;
    private Long projectId;
    private String title;
    private List<ChapterDto> chapterList = new ArrayList<>();

    private LocalDateTime createdAt;

    @Builder
    public ProjectResponse(String ownerId, Long projectId, String title, List<ChapterDto> chapterList, LocalDateTime createdAt) {
        this.ownerId = ownerId;
        this.projectId = projectId;
        this.title = title;
        this.chapterList = chapterList;
        this.createdAt = createdAt;
    }
}
