package com.pencilwith.apiserver.working;

import com.pencilwith.apiserver.domain.entity.Chapter;
import com.pencilwith.apiserver.domain.entity.ChapterStatus;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.ChapterRepository;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;

    @Transactional(readOnly = true)
    public ProjectServiceDTO.MyProjectDTO getMyProjectList() {
        User curUser = getCurUser();
        return new ProjectServiceDTO.MyProjectDTO(curUser);
    }

    @Transactional(readOnly = true)
    public ProjectServiceDTO.ProjectDTO getProject(Long id) {
        Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("프로젝트가 존재하지 않습니다."));
        return new ProjectServiceDTO.ProjectDTO(project);
    }

    @Transactional
    public ProjectServiceDTO.ProjectDTO createProject(String title) {
        User curUser = getCurUser();

        Project newProject = Project.builder()
                .owner(curUser)
                .createdAt(LocalDateTime.now())
                .title(title)
                .build();

        newProject = projectRepository.save(newProject);

        return new ProjectServiceDTO.ProjectDTO(newProject);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto createChpter(Long id, String title) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));
        Chapter chapter = Chapter.builder().project(project).createAt(LocalDateTime.now()).title(title).build();
        chapter = this.chapterRepository.save(chapter);
        project.getChapterList().add(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto publishChpter(Long projectId, Long chapterId) {
        Chapter chapter = this.chapterRepository.findById(chapterId)
                .orElseThrow(() -> new BadRequestException("해당 챕터가 존재하지 않습니다."));
        chapter.setStatus(ChapterStatus.PUBLISH);
        chapter = this.chapterRepository.save(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto modifyChapterContent(Long projectId, Long chapterId, String content) {
        Chapter chapter = this.chapterRepository.findById(chapterId)
                .orElseThrow(() -> new BadRequestException("해당 챕터가 존재하지 않습니다."));
        chapter.setContent(content);
        chapter = this.chapterRepository.save(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

//    @Transactional
//    public ProjectResponse updateProject(Long id, ProjectControllerRequestDTO projectRequest) {
//        checkProject(id, "해당 프로젝트를 변경할 수 없습니다.");
//        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
//        return ProjectMapper.toDto(newProject);
//    }
//
//    @Transactional
//    public void deleteProject(Long id) {
//        checkProject(id, "해당 프로젝트를 삭제할 수 없습니다.");
//        // 사용자의 프로젝트 중, 해당하는 프로젝트 삭제
//        projectRepository.deleteById(id);
//    }

    private Project checkProject(Long id, String msg) {
        User curUser = getCurUser();

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        User Owner = project.getOwner();
        if (Owner.getId().equals(curUser.getId()))
            throw new BadRequestException(msg);

        return project;
    }



    private User getCurUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getUsername())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다."));
    }



}
