package com.pencilwith.apiserver.my;


import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.ProjectStatus;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.*;
import com.pencilwith.apiserver.util.AWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

    final UserRepository userRepository;
    final AWSService awsService;
    final ProjectRepository projectRepository;
    final FeedbackRepository feedbackRepository;
    final ReplyRepository replyRepository;
    final CrewRecruitRepository crewRecruitRepository;


    @Transactional(readOnly = true)
    public MyDTO.UserDTO getUserInfo(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));
        return new MyDTO.UserDTO(user);
    }

    @Transactional
    public MyDTO.UserDTO modifyUserInfo(String id, MyDTO.ModifyUserDTO modifyUserDTO) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));

        if (modifyUserDTO.getName() != null)
            user.setUsername(modifyUserDTO.getName());
        if (modifyUserDTO.getBirth() != null)
            user.setBirth(modifyUserDTO.getBirth());
        if (modifyUserDTO.getCareerType() != null)
            user.setCareerType(modifyUserDTO.getCareerType());
        if (modifyUserDTO.getIntroduction() != null)
            user.setIntroduction(modifyUserDTO.getIntroduction());
        if (modifyUserDTO.getGenderType() != null)
            user.setGenderType(modifyUserDTO.getGenderType());
        if (modifyUserDTO.getLocationType() != null)
            user.setLocationType(modifyUserDTO.getLocationType());

        user = this.userRepository.save(user);

        return new MyDTO.UserDTO(user);
    }

    @Transactional
    public MyDTO.UserDTO modifyUserProfileImage(String id, MultipartFile image) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));
        String imageURL = this.awsService.upload(image);
        user.setProfileImage(imageURL);
        return new MyDTO.UserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<MyDTO.ProjectDTO> getFinishedProjects(String userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));
        List<Project> projects = this.projectRepository.findByOwnerAndStatus(user, ProjectStatus.FINISH);

        return projects.stream()
                .map(MyDTO.ProjectDTO::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public List<MyDTO.ProjectDTO> reworkProject(String userId, Long projectId) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException("프로젝트가 존재하지 않습니다."));
        project.setStatus(ProjectStatus.PROGRESS);

        return this.getFinishedProjects(userId);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));

        User curUser = this.getCurUser();
        if(!curUser.equals(user))
            throw new BadRequestException("현재 로그인한 사용자와 탈퇴 대상 사용자가 일치하지 않습니다.");

        user.getProject().forEach(project -> project.getCrewList().remove(user));
        this.crewRecruitRepository.deleteAllByOwner(user);
        this.projectRepository.deleteAllByOwner(user);
        this.feedbackRepository.deleteAllByOwner(user);
        this.replyRepository.deleteAllByOwner(user);
        this.userRepository.deleteById(user.getId());

    }

    private User getCurUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getUsername())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다."));
    }
}
