package com.pencilwith.apiserver.model.dto;

import com.pencilwith.apiserver.model.enums.CareerType;
import com.pencilwith.apiserver.model.enums.GenderType;
import com.pencilwith.apiserver.model.enums.LocationType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private String username;

    private String nickname;

    private String profileImage;

    private GenderType genderType;

    private LocalDate birth;

    private LocationType locationType;

    private CareerType careerType;

    private String introduction;

    public UserDto() {
    }

    @Builder
    public UserDto(String username, String nickname, String profileImage,
            GenderType genderType, LocalDate birth,
            LocationType locationType, CareerType careerType, String introduction) {
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.genderType = genderType;
        this.birth = birth;
        this.locationType = locationType;
        this.careerType = careerType;
        this.introduction = introduction;
    }
}
