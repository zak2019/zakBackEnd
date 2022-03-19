package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String notificationId;
    private NotificationTypeDto notificationType;
    private UserDto userToNotify;
    private EventDto event;
    private TeamDto team;
    private Date creationDate;
    private boolean consulted;
    private boolean pageConsulted;
}
