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
public class Reply {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User owner;

    private LocalDateTime createdAt;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Feedback feedback;

    @Builder
    public Reply(User owner, LocalDateTime createdAt, String content, Feedback feedback) {
        this.owner = owner;
        this.createdAt = createdAt;
        this.content = content;
        this.feedback = feedback;
    }
}
