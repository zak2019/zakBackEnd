package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDto {
    private String eventTypeId;
    private String code;
    private String name;
    private String bestPractices;
}
