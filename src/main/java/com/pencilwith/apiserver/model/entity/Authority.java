package com.pencilwith.apiserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class Authority {

    @Id
    private String authorityName;

    public Authority() {
    }

    @Builder
    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
