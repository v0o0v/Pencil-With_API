package com.pencilwith.apiserver.common.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Feedback feedback;
}
