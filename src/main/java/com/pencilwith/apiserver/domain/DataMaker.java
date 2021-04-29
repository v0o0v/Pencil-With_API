package com.pencilwith.apiserver.domain;

import com.pencilwith.apiserver.crew.NovelGenre;
import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.repository.CrewRecruitRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.start.model.enums.CareerType;
import com.pencilwith.apiserver.start.model.enums.GenderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Profile("dev")
@Component
public class DataMaker implements ApplicationRunner {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrewRecruitRepository crewRecruitRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        User user1 = User.builder()
                .id("user1")
                .birth(LocalDate.of(2000,1,1))
                .careerType(CareerType.INTERMEDIATE)
                .genderType(GenderType.MALE)
                .build();
        this.userRepository.save(user1);
        User user2 = User.builder()
                .id("user2")
                .birth(LocalDate.of(2010,2,2))
                .careerType(CareerType.NEWBIE)
                .genderType(GenderType.FEMALE)
                .build();
        this.userRepository.save(user2);
        User user3 = User.builder()
                .id("user3")
                .birth(LocalDate.of(1950,10,10))
                .careerType(CareerType.SENIOR)
                .genderType(GenderType.FEMALE)
                .build();
        this.userRepository.save(user3);

        Project project1 = new Project();
        project1.setCreatedAt(LocalDateTime.of(2021,01,01,10,15));
        project1.setTitle("Project1 프로젝트1 😊");
        project1.setOwner(user1);
        project1.getCrewList().add(user2);
        project1.getCrewList().add(user3);
        this.projectRepository.save(project1);

        Project project2 = new Project();
        project2.setCreatedAt(LocalDateTime.of(2021,02,02,13,55));
        project2.setTitle("Project2 🤞✌🎁");
        project2.setOwner(user2);
        project2.getCrewList().add(user3);
        this.projectRepository.save(project2);

        CrewRecruit crewRecruit1 = CrewRecruit.builder()
                .owner(user1)
                .notiUserList(List.of(user2,user3))
                .createdAt(LocalDateTime.of(2021,4,1,10,10))
                .startDate(LocalDate.of(1900,01,01))
                .endDate(LocalDate.of(2100,01,01))
                .content("가나다 sdsdsd 😍😍😍😍😍🤢🤢🤢🤢🤢")
                .genre(Set.of(NovelGenre.게임, NovelGenre.스릴러))
                .maxNumber(10)
                .title("크루 모집해요~ 👌👌👌😘")
                .project(project1)
                .build();
        this.crewRecruitRepository.save(crewRecruit1);

        CrewRecruit crewRecruit2 = CrewRecruit.builder()
                .owner(user2)
                .notiUserList(List.of(user3))
                .createdAt(LocalDateTime.of(2021,5,1,10,10))
                .startDate(LocalDate.of(2021,01,01))
                .endDate(LocalDate.of(2222,01,01))
                .content("❤❤❤❤❤")
                .genre(Set.of(NovelGenre.기타))
                .maxNumber(3)
                .title("invite you!! 💖")
                .project(project2)
                .build();
        this.crewRecruitRepository.save(crewRecruit2);

    }
}
