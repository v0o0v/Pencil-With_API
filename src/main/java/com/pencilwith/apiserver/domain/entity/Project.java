package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "PROJECT_CREW"
            , joinColumns = @JoinColumn(name = "PROJECT_ID")
            , inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @JsonManagedReference
    private Set<User> crewList = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Chapter> chapterList = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Feedback> feedbackList = new HashSet<>();

    private LocalDateTime createdAt;

    private String title;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Builder
    public Project(Long id, User owner, Set<User> crewList, Set<Chapter> chapterList, Set<Feedback> feedbackList, LocalDateTime createdAt, String title) {
        this.id = id;
        this.owner = owner;
        this.crewList = crewList;
        this.chapterList = chapterList;
        this.feedbackList = feedbackList;
        this.createdAt = createdAt;
        this.title = title;
        this.status = ProjectStatus.PROGRESS;
    }
}
