package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ProjectRequest {
    private User owner;
    private Long projectId;
    private String title;
    private Set<User> crewList = new HashSet<>();
    private List<ChapterDto> chapterList = new ArrayList<>();

    private LocalDateTime createdAt;

    @Builder
    public ProjectRequest(User owner, Long projectId, String title, Set<User> crewList, List<ChapterDto> chapterList, LocalDateTime createdAt) {
        this.owner = owner;
        this.projectId = projectId;
        this.title = title;
        this.crewList = crewList;
        this.chapterList = chapterList;
        this.createdAt = createdAt;
    }
}
