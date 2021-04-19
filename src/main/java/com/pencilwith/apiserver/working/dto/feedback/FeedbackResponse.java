package com.pencilwith.apiserver.working.dto.feedback;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class FeedbackResponse {
    private Long feedbackId;
    private String ownerId;
    private String ownerName;
    private Long projectId;

    private String position;

    private String content;
    private List<ReplyDto> replyList = new ArrayList<>();

    private LocalDateTime createdAt;


}
