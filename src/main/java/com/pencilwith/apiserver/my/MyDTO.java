package com.pencilwith.apiserver.my;

import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.start.model.enums.CareerType;
import com.pencilwith.apiserver.start.model.enums.GenderType;
import com.pencilwith.apiserver.start.model.enums.LocationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class MyDTO {

    @Getter
    @Setter
    public static class UserDTO {

        private String id;

        private String name;

        private GenderType genderType;

        private LocalDate birth;

        private LocationType locationType;

        private CareerType careerType;

        private String introduction;

        private String profileImage;

        public UserDTO(User user) {
            this.id = user.getId();
            this.name = user.getUsername();
            this.genderType = user.getGenderType();
            this.birth = user.getBirth();
            this.locationType = user.getLocationType();
            this.careerType = user.getCareerType();
            this.introduction = user.getIntroduction();
            this.profileImage = user.getProfileImage();
        }
    }

}
