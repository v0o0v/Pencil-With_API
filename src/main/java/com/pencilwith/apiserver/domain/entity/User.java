package com.pencilwith.apiserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @JsonIgnore
    @Id
    @Column(length = 100)
    private String id;

    @JsonIgnore
    private String password;

    @Column(unique = true, length = 100)
    private String username;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private CareerType careerType;

    private String introduction;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAuthority> userAuthorities;

    public User() {
        this.userAuthorities = new HashSet<>();
    }

    @Builder
    public User(String id, String username, String password, String profileImage,
            GenderType genderType, LocalDate birth,
            LocationType locationType, CareerType careerType, String introduction,
            Set<UserAuthority> userAuthorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.genderType = genderType;
        this.birth = birth;
        this.locationType = locationType;
        this.careerType = careerType;
        this.introduction = introduction;
        this.userAuthorities = userAuthorities == null ? new HashSet<>() : userAuthorities;
    }

    public boolean addAuthority(UserAuthority userAuthority) {
        return this.userAuthorities.add(userAuthority);
    }

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Project> ownerProjectList;

    @ManyToMany(mappedBy = "crewList")
    @JsonBackReference
    private List<Project> project;
}
