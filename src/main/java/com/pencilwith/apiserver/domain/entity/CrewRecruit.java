package com.pencilwith.apiserver.domain.entity;

import com.pencilwith.apiserver.crew.NovelGenre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class CrewRecruit {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User owner;

    private LocalDateTime createdAt;

    @OneToOne
    private Project project;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer maxNumber;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<NovelGenre> genre;

    @Lob
    private String content;

    @Builder
    public CrewRecruit(User owner, LocalDateTime createdAt, Project project, String title, LocalDate startDate, LocalDate endDate, Integer maxNumber, Set<NovelGenre> genre, String content) {
        this.owner = owner;
        this.createdAt = createdAt;
        this.project = project;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNumber = maxNumber;
        this.genre = genre;
        this.content = content;
    }
}
