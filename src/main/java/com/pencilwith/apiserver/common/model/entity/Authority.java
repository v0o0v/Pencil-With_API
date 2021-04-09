package com.pencilwith.apiserver.common.model.entity;

import com.pencilwith.apiserver.common.enums.AuthorityType;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private AuthorityType type;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAuthority> userAuthorities;

    public Authority() {
    }

    @Builder
    public Authority(Long id,
                     AuthorityType type,
                     Set<UserAuthority> userAuthorities) {
        this.id = id;
        this.type = type;
        this.userAuthorities = userAuthorities;
    }
}
