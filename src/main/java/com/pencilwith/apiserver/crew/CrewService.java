package com.pencilwith.apiserver.crew;


import com.pencilwith.apiserver.domain.entity.*;
import com.pencilwith.apiserver.domain.entity.QCrewRecruit;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.CrewRecruitRepository;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService {

    final UserRepository userRepository;

    final ProjectRepository projectRepository;

    final CrewRecruitRepository crewRecruitRepository;

    @Transactional
    public CrewServiceDTO.CrewRecruitDTO makeRecruit(CrewControllerDTO.PostRecruitRequestDTO request) {

        //존재하는 프로젝트인지
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));


        org.springframework.security.core.userdetails.User curUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!curUser.getUsername().equals(project.getOwner().getId())) {
            throw new BadRequestException("현재 로그인된 사용자의 프로젝트가 아닙니다.");
        }

        User user = this.userRepository.findById(curUser.getUsername())
                .orElseThrow(() -> new BadRequestException("로그인된 사용자의 정보가 존재하지 않습니다."));

        //해당 프로젝트에 모집이 이미 있는지
        if (crewRecruitRepository.existsByProject(project))
            throw new BadRequestException("해당 프로젝트에 이미 크루 모집 공고가 존재합니다.");

        CrewRecruit crewRecruit = CrewRecruit.builder()
                .createdAt(LocalDateTime.now())
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .owner(user)
                .content(request.getContent())
                .project(project)
                .genre(request.getGenre())
                .maxNumber(request.getMaxNumber())
                .crewRecruitState(CrewRecruitState.POST)
                .build();

        crewRecruit = this.crewRecruitRepository.save(crewRecruit);
        return new CrewServiceDTO.CrewRecruitDTO(crewRecruit);
    }

    @Transactional(readOnly = true)
    public Page<CrewServiceDTO.CrewRecruitDTO> getRecruits(Pageable pageable, CrewServiceDTO.CrewRecruitFilterDTO requestDTO) {
        QCrewRecruit crewRecruit = QCrewRecruit.crewRecruit;
        BooleanBuilder bb = new BooleanBuilder();

        bb.and(crewRecruit.state.eq(CrewRecruitState.POST));

        if (requestDTO.getGenderTypes() != null)
            bb.and(crewRecruit.owner.genderType.in(requestDTO.getGenderTypes()));

        if (requestDTO.getCareerTypes() != null)
            bb.and(crewRecruit.owner.careerType.in(requestDTO.getCareerTypes()));

        if (requestDTO.getNovelGenres() != null)
            bb.and(crewRecruit.genre.any().in(requestDTO.getNovelGenres()));

        LocalDate now = LocalDate.now();

        if (requestDTO.getMinAge() != null)
            bb.and(crewRecruit.owner.birth.before(now.minusYears(requestDTO.getMinAge())));

        if (requestDTO.getMaxAge() != null)
            bb.and(crewRecruit.owner.birth.after(now.minusYears(requestDTO.getMaxAge())));

        return this.crewRecruitRepository.findAll(bb.getValue(), pageable).map(CrewServiceDTO.CrewRecruitDTO::new);
    }

    @Transactional
    public CrewServiceDTO.ProjectDTO joinRecruit(Long id, String userId) {
        CrewRecruit crewRecruit = this.crewRecruitRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("크루 모집 아이디가 존재하지 않습니다."));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("유저 아이디가 존재하지 않습니다."));

        if (user.equals(crewRecruit.getProject().getOwner()))
            throw new BadRequestException("해당 프로젝트의 Owner 유저는 크루가 될 수 없습니다.");

        Project project = crewRecruit.getProject();
        project.getCrewList().add(user);
        project = this.projectRepository.save(project);

        return new CrewServiceDTO.ProjectDTO(project);
    }

    @Transactional
    public CrewServiceDTO.CrewRecruitDTO getRecruit(Long id) {
        CrewRecruit crewRecruit = this.crewRecruitRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("크루 모집 아이디가 존재하지 않습니다."));
        return new CrewServiceDTO.CrewRecruitDTO(crewRecruit);
    }
}
