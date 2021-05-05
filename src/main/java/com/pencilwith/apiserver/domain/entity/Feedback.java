package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User owner;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Project project;

    private LocalDateTime createdAt;

    private String position;

    @Lob
    private String content;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reply> replyList = new ArrayList<>();

    @Builder
    public Feedback(Long id, User owner, Project project, LocalDateTime createdAt, String position, String content, List<Reply> replyList) {
        this.id = id;
        this.owner = owner;
        this.project = project;
        this.createdAt = createdAt;
        this.position = position;
        this.content = content;
        this.replyList = replyList;
    }
}
