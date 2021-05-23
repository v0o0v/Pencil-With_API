package com.pencilwith.apiserver.working;

import com.pencilwith.apiserver.domain.entity.*;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.*;
import com.pencilwith.apiserver.util.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final AWSService awsService;
    private final FeedbackRepository feedbackRepository;
    private final CrewRecruitRepository crewRecruitRepository;
    private final ReplyRepository replyRepository;

    private void hasRight(Project project, User user) {
        if (!project.getOwner().getId().equals(user.getId()))
            throw new BadRequestException("프로젝트의 Owner가 아닙니다.");
    }

    private void hasRight(Feedback feedback, User user) {
        if (!feedback.getOwner().getId().equals(user.getId()))
            throw new BadRequestException("Feedback의 Owner가 아닙니다.");
    }

    private void hasRight(Reply reply, User user) {
        if (!reply.getOwner().getId().equals(user.getId()))
            throw new BadRequestException("Reply의 Owner가 아닙니다.");
    }

    private User getCurUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getUsername())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다."));
    }

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
    public ProjectServiceDTO.ProjectDTO modifyProject(Long id, String title) {
        Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("프로젝트가 존재하지 않습니다."));
        if (title != null)
            project.setTitle(title);
        return new ProjectServiceDTO.ProjectDTO(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("프로젝트가 존재하지 않습니다."));

        User user = getCurUser();
        hasRight(project, user);

        this.crewRecruitRepository.deleteAllByProject(project);
        this.projectRepository.delete(project);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto createChapter(Long id, String title) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        this.hasRight(project, this.getCurUser());

        Chapter chapter = Chapter.builder().project(project).createAt(LocalDateTime.now()).title(title).build();
        chapter = this.chapterRepository.save(chapter);
        project.getChapterList().add(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto publishChapter(Long projectId, Long chapterId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        this.hasRight(project, this.getCurUser());

        Chapter chapter = this.chapterRepository.findById(chapterId)
                .orElseThrow(() -> new BadRequestException("해당 챕터가 존재하지 않습니다."));
        chapter.setStatus(ChapterStatus.PUBLISH);
        chapter = this.chapterRepository.save(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

    @Transactional
    public ProjectServiceDTO.ChapterDto modifyChapterContent(Long projectId, Long chapterId, String content) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        this.hasRight(project, this.getCurUser());

        Chapter chapter = this.chapterRepository.findById(chapterId)
                .orElseThrow(() -> new BadRequestException("해당 챕터가 존재하지 않습니다."));
        chapter.setContent(content);
        chapter = this.chapterRepository.save(chapter);

        return new ProjectServiceDTO.ChapterDto(chapter);
    }

    @Transactional
    public ProjectServiceDTO.ProjectDTO finishProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        this.hasRight(project, this.getCurUser());

        project.setStatus(ProjectStatus.FINISH);
        project = this.projectRepository.save(project);

        return new ProjectServiceDTO.ProjectDTO(project);
    }

    @Transactional
    public ProjectServiceDTO.FeedbackDTO createFeedback(Long id, String content, String position, MultipartFile soundFile) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        User user = this.getCurUser();

        String fileURL = null;
        if (soundFile != null)
            fileURL = this.awsService.upload(soundFile);

        Feedback feedback = Feedback.builder()
                .createdAt(LocalDateTime.now())
                .project(project)
                .content(content)
                .position(position)
                .soundURL(fileURL)
                .owner(user)
                .build();

        feedback = this.feedbackRepository.save(feedback);

        return new ProjectServiceDTO.FeedbackDTO(feedback);
    }

    @Transactional(readOnly = true)
    public Page<ProjectServiceDTO.FeedbackDTO> getFeedbackList(Long id, Pageable pageable) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        Page<Feedback> feedbacks = this.feedbackRepository.findByProjectOrderByCreatedAtDesc(project, pageable);

        return feedbacks.map(ProjectServiceDTO.FeedbackDTO::new);
    }

    @Transactional(readOnly = true)
    public ProjectServiceDTO.FeedbackDTO getFeedback(Long projectId, Long feedbackId) {
        Feedback feedback = this.feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BadRequestException("해당 피드백이 존재하지 않습니다."));

        return new ProjectServiceDTO.FeedbackDTO(feedback);
    }

    @Transactional
    public ProjectServiceDTO.FeedbackDTO modifyFeedback(Long projectId, Long feedbackId, String content, String position) {
        Feedback feedback = this.feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BadRequestException("해당 피드백이 존재하지 않습니다."));

        if (content != null)
            feedback.setContent(content);
        if (position != null)
            feedback.setPosition(position);

        feedback = this.feedbackRepository.save(feedback);

        return new ProjectServiceDTO.FeedbackDTO(feedback);
    }

    @Transactional
    public void deleteFeedback(Long projectId, Long feedbackId) {
        Feedback feedback = this.feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BadRequestException("해당 피드백이 존재하지 않습니다."));

        User user = getCurUser();
        hasRight(feedback, user);

        this.feedbackRepository.delete(feedback);
    }

    @Transactional
    public ProjectServiceDTO.FeedbackDTO addReply(Long projectId, Long feedbackId, String content) {
        Feedback feedback = this.feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BadRequestException("해당 피드백이 존재하지 않습니다."));

        User user = this.getCurUser();

        Reply reply = Reply.builder().owner(user).createdAt(LocalDateTime.now()).content(content).feedback(feedback).build();
        feedback.getReplyList().add(reply);

        return new ProjectServiceDTO.FeedbackDTO(feedback);
    }

    @Transactional
    public ProjectServiceDTO.FeedbackDTO deleteReply(Long projectId, Long feedbackId, Long replyId) {
        Reply reply = this.replyRepository.findById(replyId)
                .orElseThrow(() -> new BadRequestException("해당 Reply가 존재하지 않습니다."));

        if (reply.getFeedback().getId() != feedbackId)
            throw new BadRequestException("삭제 요청 한 reply은 해당 feedback에 존재하지 않습니다.");

        User user = this.getCurUser();
        hasRight(reply, user);

        Feedback feedback = this.feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BadRequestException("해당 피드백이 존재하지 않습니다."));
        feedback.getReplyList().remove(reply);
        feedback = this.feedbackRepository.save(feedback);

        return new ProjectServiceDTO.FeedbackDTO(feedback);
    }

}
