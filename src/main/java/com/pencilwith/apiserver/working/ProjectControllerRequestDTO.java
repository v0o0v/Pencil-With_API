package com.pencilwith.apiserver.working;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class ProjectControllerRequestDTO {

    @Getter
    @Setter
    public static class ProjectCreateRequestDTO {

        @NonNull
        private String title;

    }

}
