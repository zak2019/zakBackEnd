package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventCommentDto {

    private String eventCommentId;
    private String comment;
    private EventDto event;
    private SecretUserDto user;
    private Date creationDate;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
}
