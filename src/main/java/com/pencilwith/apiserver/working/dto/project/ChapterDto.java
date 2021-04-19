package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.Chapter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChapterDto {
    private Long chapterId;
    private String content;

    public ChapterDto toChapterDto(Chapter chapter) {
        return ChapterDto.builder()
                .chapterId(chapter.getId())
                .content(chapter.getContent())
                .build();
    }

    public void toEntity(ChapterDto chapterDto) {
        //
    }
}
