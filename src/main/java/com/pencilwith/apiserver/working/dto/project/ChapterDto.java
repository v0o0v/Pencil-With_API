package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.Chapter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ChapterDto {
    private Long chapterId;
    private String content;

    @Builder
    public ChapterDto(Long chapterId, String content) {
        this.chapterId = chapterId;
        this.content = content;
    }
}
