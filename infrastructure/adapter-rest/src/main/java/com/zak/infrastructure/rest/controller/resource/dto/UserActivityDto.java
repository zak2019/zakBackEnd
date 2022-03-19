package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDto {

    private String userActivityId;
    private UserDto user;
    private EventCommentDto eventComment;
    private EventDto event;
    private Date creationDate;
}
