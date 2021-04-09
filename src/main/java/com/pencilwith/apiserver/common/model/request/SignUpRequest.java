package com.pencilwith.apiserver.common.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pencilwith.apiserver.common.model.dto.UserInfoResponseDto;
import com.pencilwith.apiserver.common.enums.CareerType;
import com.pencilwith.apiserver.common.enums.GenderType;
import com.pencilwith.apiserver.common.enums.LocationType;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotNull
    private String authorizationCode;

    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    private String username;

    private String password;

    private String profileImage;

    @NotNull
    private GenderType genderType;

    @NotNull
    @JsonFormat(pattern = "yyyy.MM.dd")
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
        UserInfoResponseDto dto = userInfoStorage.get(this.authorizationCode);
        if (dto == null) {
            throw new IllegalArgumentException();
        }

        this.id = dto.getLoginType() + dto.getUserId();
        this.password = dto.getUserId();

        userInfoStorage.remove(this.authorizationCode);
    }
}
