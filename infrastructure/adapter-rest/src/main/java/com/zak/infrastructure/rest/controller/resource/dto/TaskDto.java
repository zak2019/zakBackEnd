package com.zak.infrastructure.rest.controller.resource.dto;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.User;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String taskId;
    private String title;
    private String text;
    private UserDto createdBy;
    private UserDto affectedTo;
    private TeamDto team;
    private AccountDto account;
    private Date creationDate;
    private EventStatus status;
    private int position;
}
