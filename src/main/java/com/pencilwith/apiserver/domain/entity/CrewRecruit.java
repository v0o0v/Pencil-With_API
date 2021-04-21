package com.pencilwith.apiserver.domain.entity;

import com.pencilwith.apiserver.crew.NovelGenre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private NovelGenre genre;

    @Lob
    private String content;

    @OneToMany
    private List<User> notiUserList = new ArrayList<>();

    @Builder
    public CrewRecruit(User owner, LocalDateTime createdAt, Project project, String title, LocalDate startDate, LocalDate endDate, Integer maxNumber, NovelGenre genre, String content, List<User> notiUserList) {
        this.owner = owner;
        this.createdAt = createdAt;
        this.project = project;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNumber = maxNumber;
        this.genre = genre;
        this.content = content;
        this.notiUserList = notiUserList;
    }
}
