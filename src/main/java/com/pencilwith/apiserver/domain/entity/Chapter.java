package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Chapter {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Project project;

    @Lob
    private String content;

    private LocalDateTime createAt;

    private String title;

    private ChapterStatus status;

    @Builder
    public Chapter(Project project, String content, LocalDateTime createAt, String title, ChapterStatus status) {
        this.project = project;
        this.content = content;
        this.createAt = createAt;
        this.title = title;
        this.status = status;
    }
}
