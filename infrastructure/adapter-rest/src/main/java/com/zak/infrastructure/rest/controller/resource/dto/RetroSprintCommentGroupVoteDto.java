package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintCommentGroupVoteDto {

    private String retroSprintCommentGroupVoteId;
    private UserDto user;
    private RetroSprintCommentGroupDto retroSprintCommentGroup;
    private Date creationDate;
    private int votesNumber;
}
