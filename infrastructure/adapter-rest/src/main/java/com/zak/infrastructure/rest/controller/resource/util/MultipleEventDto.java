package com.zak.infrastructure.rest.controller.resource.util;

import com.zak.infrastructure.rest.controller.resource.dto.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleEventDto {

    private EventDto event;
    private Set<String> dateList;
    private String eventHour;
    private String eventEndHour;
}
