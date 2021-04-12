package com.pencilwith.apiserver;

import com.pencilwith.apiserver.auth.repository.UserRepository;
import com.pencilwith.apiserver.common.model.entity.Project;
import com.pencilwith.apiserver.common.model.entity.User;
import com.pencilwith.apiserver.common.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DataMaker implements ApplicationRunner {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Project project1 = new Project();
        Project project2 = new Project();

        User user1 = User.builder().id("user1").build();
        this.userRepository.save(user1);
        User user2 = User.builder().id("user2").build();
        this.userRepository.save(user2);
        User user3 = User.builder().id("user3").build();
        this.userRepository.save(user3);

        project1.setOwner(user1);
        project1.getCrewList().add(user2);
        project1.getCrewList().add(user3);

        project2.setOwner(user2);
        project2.getCrewList().add(user3);

        this.projectRepository.save(project1);
        this.projectRepository.save(project2);

    }
}
