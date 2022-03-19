package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTypeDto {

    private String notificationTypeId;
    private String code;
    private String name;
}
