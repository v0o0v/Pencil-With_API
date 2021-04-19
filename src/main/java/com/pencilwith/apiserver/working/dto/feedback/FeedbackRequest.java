package com.pencilwith.apiserver.working.dto.feedback;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FeedbackRequest {
    private Long projectId;
    private Long feedbackId;
    private Long ownerId;
    private String content;

    private String position;

    private LocalDateTime createdAt;
}
