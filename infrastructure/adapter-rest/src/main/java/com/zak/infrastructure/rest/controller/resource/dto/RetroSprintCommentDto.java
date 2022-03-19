package com.zak.infrastructure.rest.controller.resource.dto;

import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.domain.model.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintCommentDto {

    private String retroSprintCommentId;
    private String comment;
    private User user;
    private RetroSprintCommentGroupDto retroSprintCommentGroup;
    private Date creationDate;
    private int commentPosition;
    private RetroSprintCommentType commentType;
    private Set<RetroSprintCommentVoteDto> retroSprintCommentVoteSet;
}
