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

    @Enumerated(EnumType.STRING)
    private ChapterStatus status;

    @Builder
    public Chapter(Project project, LocalDateTime createAt, String title) {
        this.project = project;
        this.createAt = createAt;
        this.title = title;
        this.status = ChapterStatus.DRAFT;
    }
}
