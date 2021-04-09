package com.pencilwith.apiserver.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pencilwith.apiserver.common.enums.CareerType;
import com.pencilwith.apiserver.common.enums.GenderType;
import com.pencilwith.apiserver.common.enums.LocationType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @JsonIgnore
    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

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
}
