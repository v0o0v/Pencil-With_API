package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Chapter;
import com.pencilwith.apiserver.working.dto.project.ChapterDto;

import java.util.List;
import java.util.stream.Collectors;

public class ChapterMapper {
    public static List<ChapterDto> toDto(List<Chapter> chapters) {
        return chapters.stream().map(chapter -> ChapterDto.builder()
                    .chapterId(chapter.getId())
                    .content(chapter.getContent())
        .build()).collect(Collectors.toList());
    }
}
