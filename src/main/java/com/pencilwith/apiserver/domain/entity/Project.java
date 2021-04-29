package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
