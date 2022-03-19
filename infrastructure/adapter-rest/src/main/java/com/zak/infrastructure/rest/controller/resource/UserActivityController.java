package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.UserActivityService;
import com.zak.domain.model.UserActivity;
import com.zak.infrastructure.rest.controller.resource.dto.UserActivityDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/user-activity")
public class UserActivityController {

    private final UserActivityService userActivityService;
    private final MapperFacade orikaMapperFacade;

    public UserActivityController(UserActivityService userActivityService, MapperFacade orikaMapperFacade) {
        this.userActivityService = userActivityService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/user/{userId}")
    public Set<UserActivityDto> getUserActivitiesByUserId(@PathVariable String userId) {
        Set<UserActivity> activities = userActivityService.getUserActivitiesByUserId(userId);

        return orikaMapperFacade.mapAsSet(activities, UserActivityDto.class);
    }

}
