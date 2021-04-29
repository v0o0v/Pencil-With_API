package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProjectRequest {
    private String ownerId;
    private Long projectId;
    private String title;
    private List<ChapterDto> chapterList = new ArrayList<>();

    private LocalDateTime createdAt;
}
