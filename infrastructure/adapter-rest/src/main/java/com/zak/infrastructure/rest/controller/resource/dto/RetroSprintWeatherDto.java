package com.zak.infrastructure.rest.controller.resource.dto;

import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.User;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetroSprintWeatherDto {


    private String retroSprintWeatherId;
    private User user;
    private RetroSprint retroSprint;
    private boolean sunnyClear;
    private boolean sunnyCloud;
    private boolean rainy;
    private boolean storm;
    private Date creationDate;
}
