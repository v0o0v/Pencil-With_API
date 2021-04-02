package com.pencilwith.apiserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pencilwith.apiserver.model.enums.CareerType;
import com.pencilwith.apiserver.model.enums.GenderType;
import com.pencilwith.apiserver.model.enums.LocationType;
import java.time.LocalDate;
import java.util.HashSet;
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
public class User {

    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String nickName;

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
    private Set<UserAuthorityMapping> userAuthorityMappings;

    public User() {
        this.userAuthorityMappings = new HashSet<>();
    }

    @Builder
    public User(Long id, String username, String password, String nickName,
            String profileImage, GenderType genderType, LocalDate birth,
            LocationType locationType, CareerType careerType, String introduction,
            Set<UserAuthorityMapping> userAuthorityMappings) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.genderType = genderType;
        this.birth = birth;
        this.locationType = locationType;
        this.careerType = careerType;
        this.introduction = introduction;
        this.userAuthorityMappings = userAuthorityMappings == null ? new HashSet<>() : userAuthorityMappings;
    }

    public boolean addAuthority(UserAuthorityMapping userAuthorityMapping) {
        return this.userAuthorityMappings.add(userAuthorityMapping);
    }
}
