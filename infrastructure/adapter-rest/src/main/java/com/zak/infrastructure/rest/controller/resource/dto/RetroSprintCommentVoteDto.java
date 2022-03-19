package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintCommentVoteDto {

    private String retroSprintCommentVoteId;
    private RetroSprintCommentDto retroSprintComment;
    private UserDto user;
    private Date creationDate;
}
