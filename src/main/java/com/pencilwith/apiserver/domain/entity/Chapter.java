package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

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

    @Builder
    public Chapter(Long id, Project project, String content) {
        this.id = id;
        this.project = project;
        this.content = content;
    }
}
