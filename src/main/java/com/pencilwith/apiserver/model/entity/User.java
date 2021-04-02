package com.pencilwith.apiserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pencilwith.apiserver.model.enums.Career;
import com.pencilwith.apiserver.model.enums.Gender;
import com.pencilwith.apiserver.model.enums.Location;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Account {

    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickName;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private Career career;

    private String introduction;

    public Account() {
    }

    @Builder
    public Account(Long id, String nickName, String profileImage,
            Gender gender, LocalDate birth, Location location,
            Career career, String introduction) {
        this.id = id;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.gender = gender;
        this.birth = birth;
        this.location = location;
        this.career = career;
        this.introduction = introduction;
    }
}
