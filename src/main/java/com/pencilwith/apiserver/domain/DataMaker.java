package com.pencilwith.apiserver.domain;

import com.pencilwith.apiserver.domain.entity.*;
import com.pencilwith.apiserver.domain.repository.*;
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

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        User user1 = User.builder()
                .id("user1")
                .birth(LocalDate.of(2000,1,1))
                .careerType(CareerType.INTERMEDIATE)
                .genderType(GenderType.MALE)
                .build();
        user1 = this.userRepository.save(user1);
        User user2 = User.builder()
                .id("user2")
                .birth(LocalDate.of(2010,2,2))
                .careerType(CareerType.NEWBIE)
                .genderType(GenderType.FEMALE)
                .build();
        user2 = this.userRepository.save(user2);
        User user3 = User.builder()
                .id("user3")
                .birth(LocalDate.of(1950,10,10))
                .careerType(CareerType.SENIOR)
                .genderType(GenderType.FEMALE)
                .build();
        user3 = this.userRepository.save(user3);

        Project project1 = new Project();
        project1.setCreatedAt(LocalDateTime.of(2021,01,01,10,15));
        project1.setTitle("Project1 프로젝트1 😊");
        project1.setOwner(user1);
        project1.setStatus(ProjectStatus.PROGRESS);
        project1 = this.projectRepository.save(project1);

        Feedback feedback1 = Feedback.builder()
                .owner(user1).createdAt(LocalDateTime.of(1900,1,1,1,1)).content("111111")
                .project(project1).position("1-1").build();
        Reply reply1 = Reply.builder().owner(user2).createdAt(LocalDateTime.of(1900,1,1,1,1))
        .content("reply1").feedback(feedback1).build();
        feedback1.getReplyList().add(reply1);
        Reply reply2 = Reply.builder().owner(user3).createdAt(LocalDateTime.of(1901,1,1,1,1))
                .content("reply2").feedback(feedback1).build();
        feedback1.getReplyList().add(reply2);
        project1.getFeedbackList().add(feedback1);
        project1 = this.projectRepository.save(project1);

        Feedback feedback2 = Feedback.builder()
                .owner(user1).createdAt(LocalDateTime.of(1910,1,1,1,1)).content("222222")
                .project(project1).position("2-2").build();
        Reply reply3 = Reply.builder().owner(user2).createdAt(LocalDateTime.of(1900,1,1,1,1))
                .content("reply3").feedback(feedback2).build();
        feedback2.getReplyList().add(reply3);
        Reply reply4 = Reply.builder().owner(user1).createdAt(LocalDateTime.of(1901,1,1,1,1))
                .content("reply4").feedback(feedback2).build();
        feedback2.getReplyList().add(reply4);
        project1.getFeedbackList().add(feedback2);
        project1 = this.projectRepository.save(project1);

        Project project2 = new Project();
        project2.setCreatedAt(LocalDateTime.of(2021,02,02,13,55));
        project2.setTitle("Project2 🤞✌🎁");
        project2.setOwner(user2);
        project2.setStatus(ProjectStatus.PROGRESS);
        project2.getCrewList().add(user1);
        project2 = this.projectRepository.save(project2);
        Feedback feedback3 = Feedback.builder()
                .owner(user1).createdAt(LocalDateTime.of(1910,1,1,1,1)).content("33333333")
                .project(project2).position("3-2").build();
        Reply reply5 = Reply.builder().owner(user2).createdAt(LocalDateTime.of(1900,1,1,1,1))
                .content("reply5").feedback(feedback3).build();
        feedback3.getReplyList().add(reply5);
        Reply reply6 = Reply.builder().owner(user1).createdAt(LocalDateTime.of(1901,1,1,1,1))
                .content("reply6").feedback(feedback3).build();
        feedback3.getReplyList().add(reply6);
        Feedback feedback4 = Feedback.builder()
                .owner(user2).createdAt(LocalDateTime.of(1910,1,1,1,1)).content("44444444")
                .project(project2).position("4-2").build();
        Reply reply7 = Reply.builder().owner(user1).createdAt(LocalDateTime.of(1900,1,1,1,1))
                .content("reply7").feedback(feedback4).build();
        feedback4.getReplyList().add(reply7);
        Reply reply8 = Reply.builder().owner(user2).createdAt(LocalDateTime.of(1901,1,1,1,1))
                .content("reply8").feedback(feedback4).build();
        feedback4.getReplyList().add(reply8);
        project2.getFeedbackList().add(feedback4);
        project2 = this.projectRepository.save(project2);

        Project project3 = new Project();
        project3.setCreatedAt(LocalDateTime.of(2022,02,02,13,55));
        project3.setTitle("Project3 🤞✌🎁");
        project3.setOwner(user3);
        project3.setStatus(ProjectStatus.FINISH);
        project3 = this.projectRepository.save(project3);

        CrewRecruit crewRecruit1 = CrewRecruit.builder()
                .owner(user1)
                .createdAt(LocalDateTime.of(2021,4,1,10,10))
                .startDate(LocalDate.of(1900,01,01))
                .endDate(LocalDate.of(2100,01,01))
                .content("가나다 sdsdsd 😍😍😍😍😍🤢🤢🤢🤢🤢")
                .genre(Set.of(NovelGenre.게임, NovelGenre.스릴러))
                .maxNumber(10)
                .title("크루 모집해요~ 👌👌👌😘")
                .project(project1)
                .crewRecruitState(CrewRecruitState.POST)
                .build();
        crewRecruit1 = this.crewRecruitRepository.save(crewRecruit1);

        CrewRecruit crewRecruit2 = CrewRecruit.builder()
                .owner(user2)
                .createdAt(LocalDateTime.of(2021,5,1,10,10))
                .startDate(LocalDate.of(2021,01,01))
                .endDate(LocalDate.of(2222,01,01))
                .content("❤❤❤❤❤")
                .genre(Set.of(NovelGenre.기타))
                .maxNumber(3)
                .title("invite you!! 💖")
                .project(project2)
                .crewRecruitState(CrewRecruitState.POST)
                .build();
        crewRecruit2 = this.crewRecruitRepository.save(crewRecruit2);

        CrewRecruit crewRecruit3 = CrewRecruit.builder()
                .owner(user3)
                .createdAt(LocalDateTime.of(2021,5,1,10,10))
                .startDate(LocalDate.of(2021,01,01))
                .endDate(LocalDate.of(2222,01,01))
                .content("3333333333333333")
                .genre(Set.of(NovelGenre.무협))
                .maxNumber(33)
                .title("3333333333333333333")
                .project(project3)
                .crewRecruitState(CrewRecruitState.FINISH)
                .build();
        crewRecruit3 = this.crewRecruitRepository.save(crewRecruit3);

    }
}
