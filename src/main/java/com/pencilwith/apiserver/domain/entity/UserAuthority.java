package com.pencilwith.apiserver.domain.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "authority_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Authority authority;

    public UserAuthority() {
    }

    @Builder
    public UserAuthority(Long id, User user, Authority authority) {
        this.id = id;
        this.user = user;
        this.authority = authority;
    }
}
