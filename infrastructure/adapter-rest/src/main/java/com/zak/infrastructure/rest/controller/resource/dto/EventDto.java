package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private String eventId;
    private String name;
    private EventTypeDto eventType;
    private Set<UserDto> users;
    private UserDto createdBy;
    private Set<EventWeatherDto> eventWeatherSet;
    private Set<EventCommentDto> eventCommentSet;
    private TeamDto team;
    private AccountDto account;
    private Date creationDate;
    private Date eventDate;
    private Date eventEndDate;
    private Date startedAt;
}
