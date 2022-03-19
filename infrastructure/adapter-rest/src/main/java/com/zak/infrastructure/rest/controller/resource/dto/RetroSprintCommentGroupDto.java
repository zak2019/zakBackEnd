package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintCommentGroupDto {

    private String retroSprintCommentGroupId;
    private String name;
    private RetroSprintDto retroSprint;
    private Set<RetroSprintCommentDto> retroSprintCommentSet;
    private Set<RetroSprintCommentGroupVoteDto> retroSprintCommentGroupVoteSet;
    private Date creationDate;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
    private boolean voteStarted;
}
