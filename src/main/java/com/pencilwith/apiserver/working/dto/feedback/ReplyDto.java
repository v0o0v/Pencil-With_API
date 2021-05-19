package com.pencilwith.apiserver.working.dto.feedback;

import com.pencilwith.apiserver.domain.entity.Feedback;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReplyDto {
    private Long replyId;
    private String ownerName;
    private String content;
    private Long feedbackId;

    private LocalDateTime createdAt;
}
