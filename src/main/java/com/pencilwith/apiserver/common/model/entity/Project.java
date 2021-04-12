package com.pencilwith.apiserver.common.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "PROJECT_CREW"
            , joinColumns = @JoinColumn(name = "PROJECT_ID")
            , inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> crewList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Feedback> feedbackList = new ArrayList<>();

    private LocalDateTime createdAt;

    private String title;

}
