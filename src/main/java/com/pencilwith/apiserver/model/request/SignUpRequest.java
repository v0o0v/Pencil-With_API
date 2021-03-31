package com.pencilwith.apiserver.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pencilwith.apiserver.model.enums.Career;
import com.pencilwith.apiserver.model.enums.Gender;
import com.pencilwith.apiserver.model.enums.Location;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Size(max = 10)
    @NotBlank
    private String nickName;

    private String profileImage;

    @NotNull
    private Gender gender;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @NotNull
    private LocalDate birth;

    @NotNull
    private Location location;

    @NotNull
    private Career career;

    @Size(max = 80)
    private String introduction;

    public SignUpRequest() {
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", birth=" + birth +
                ", location=" + location +
                ", career=" + career +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
