package com.pencilwith.apiserver.start.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pencilwith.apiserver.start.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.domain.entity.CareerType;
import com.pencilwith.apiserver.domain.entity.GenderType;
import com.pencilwith.apiserver.domain.entity.LocationType;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotNull
    private String accessToken;

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String password;

    @NotNull
    @Size(min = 3, max = 10)
    private String username;

    @JsonIgnore
    private String profileImage;

    @NotNull
    private GenderType genderType;

    @NotNull
    private LocalDate birth;

    @NotNull
    private LocationType locationType;

    @NotNull
    private CareerType careerType;

    @Size(max = 80)
    private String introduction;

    public SignUpRequest() {
    }

    public void findIdPassword(LinkedHashMap<String, UserInfoResponseDto> userInfoStorage) {
        UserInfoResponseDto dto = userInfoStorage.get(this.accessToken);
        if (dto == null) {
            throw new IllegalArgumentException();
        }

        this.id = dto.getLoginType() + dto.getUserId();
        this.password = dto.getUserId();
        this.profileImage = dto.getProfileImage();

        userInfoStorage.remove(this.accessToken);
    }
}
