package com.zak.infrastructure.rest.controller.resource.dto;
import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.Event;
import com.zak.domain.model.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintDto {

    private String retroSprintId;
    private String retroName;
    private Event eventRetro;
    private User createdBy;
    private Date creationDate;
    private Set<RetroSprintWeatherDto> retroSprintWeatherSet;
    private Set<RetroSprintCommentGroupDto> retroSprintCommentGroupSet;
    private EventStatus weatherStatus;
    private EventStatus boardStatus;
    private boolean red;
    private boolean blue;
    private boolean green;
    private boolean yellow;
    private EventStatus status;
}
