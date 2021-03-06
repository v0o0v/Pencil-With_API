package com.pencilwith.apiserver.my;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.ProjectStatus;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.entity.CareerType;
import com.pencilwith.apiserver.domain.entity.GenderType;
import com.pencilwith.apiserver.domain.entity.LocationType;
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

    @Getter
    @Setter
    public static class ModifyUserDTO {

        private String name;

        private GenderType genderType;

        private LocalDate birth;

        private LocationType locationType;

        private CareerType careerType;

        private String introduction;

    }

    @Getter
    @Setter
    public static class ProjectDTO {

        private Long id;
        private String title;
        private ProjectStatus status;

        public ProjectDTO(Project project) {
            this.id = project.getId();
            this.title = project.getTitle();
            this.status = project.getStatus();
        }
    }

}
