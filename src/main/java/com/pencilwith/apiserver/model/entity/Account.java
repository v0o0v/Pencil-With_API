package com.pencilwith.apiserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    public Account() {
    }

    @Builder
    public Account(Long id) {
        this.id = id;
    }
}
