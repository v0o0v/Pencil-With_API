package com.pencilwith.apiserver.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pencilwith.apiserver.model.enums.CareerType;
import com.pencilwith.apiserver.model.enums.GenderType;
import com.pencilwith.apiserver.model.enums.LocationType;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 3, max = 10)
    private String nickName;

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

    @Override
    public String toString() {
        return "AccountDto{" +
                "nickName='" + nickName + '\'' +
                ", gender=" + genderType +
                ", birth=" + birth +
                ", location=" + locationType +
                ", career=" + careerType +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
