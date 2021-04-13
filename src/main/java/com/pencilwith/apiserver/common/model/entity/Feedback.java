package com.pencilwith.apiserver.common.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(mappedBy = "feedback")
    @JsonManagedReference
    private List<Reply> replyList = new ArrayList<>();

}
