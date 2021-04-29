package com.pencilwith.apiserver.crew;


import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.exception.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.CrewRecruitRepository;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService {

    final UserRepository userRepository;

    final ProjectRepository projectRepository;

    final CrewRecruitRepository crewRecruitRepository;

    @Transactional
    public CrewServiceDTO.CrewRecruitDTO makeRecruit(CrewControllerDTO.RecruitRequestDTO request) {

        //owner ID가 존재하는지
        User user = this.userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new BadRequestException("해당 사용자가 존재하지 않습니다."));

        //존재하는 프로젝트인지
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        //request의 owner와 프로젝트의 owner가 일치하는지
        if (!user.getOwnerProjectList().stream()
                .map(Project::getId)
                .collect(Collectors.toList())
                .contains(request.getProjectId()))
            throw new BadRequestException("해당 사용자의 프로젝트가 아닙니다.");

        //해당 프로젝트에 모집이 이미 있는지
        if(crewRecruitRepository.existsByProject(project))
            throw new BadRequestException("해당 프로젝트에 이미 크루 모집 공고가 존재합니다.");

        //노티 유저의 ID가 다 존재하는지
        List<User> userList = userRepository.findAllById(request.getUserIdListToNoti());
        if (userList.size() != request.getUserIdListToNoti().size()) {
            throw new BadRequestException("ID가 없는 사용자가 존재합니다.");
        }

        CrewRecruit crewRecruit = CrewRecruit.builder()
                .createdAt(LocalDateTime.now())
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .owner(user)
                .content(request.getContent())
                .project(project)
                .genre(request.getGenre())
                .notiUserList(userList)
                .maxNumber(request.getMaxNumber())
                .build();

        crewRecruit = this.crewRecruitRepository.save(crewRecruit);
        return new CrewServiceDTO.CrewRecruitDTO(crewRecruit);
    }

    @Transactional(readOnly = true)
    public Page<CrewServiceDTO.CrewRecruitDTO> getRecruits(Pageable pageable) {
        return this.crewRecruitRepository.findAll(pageable).map(CrewServiceDTO.CrewRecruitDTO::new);
    }

    @Transactional
    public CrewServiceDTO.ProjectDTO joinRecruit(Long id, String userId) {
        CrewRecruit crewRecruit = this.crewRecruitRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("크루 모집 아이디가 존재하지 않습니다."));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));

        if(user.equals(crewRecruit.getProject().getOwner()))
            throw new BadRequestException("해당 프로젝트의 Owner 유저는 크루가 될 수 없습니다.");

        Project project = crewRecruit.getProject();
        project.getCrewList().add(user);
        project = this.projectRepository.save(project);

        return new CrewServiceDTO.ProjectDTO(project);
    }
}
