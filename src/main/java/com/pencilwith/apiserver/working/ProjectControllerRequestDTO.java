package com.pencilwith.apiserver.working;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProjectControllerRequestDTO {

    @Getter
    @Setter
    public static class ProjectCreateRequestDTO {

        @NotEmpty
        private String title;

    }

    @Getter
    @Setter
    public static class ChapterCreateRequestDTO {

        @NotEmpty
        private String title;

    }

}
