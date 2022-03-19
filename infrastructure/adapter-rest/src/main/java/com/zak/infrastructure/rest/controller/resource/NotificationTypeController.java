package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.NotificationTypeService;
import com.zak.domain.model.NotificationType;
import com.zak.infrastructure.rest.controller.resource.dto.NotificationTypeDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/notificationType")
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public NotificationTypeController(NotificationTypeService notificationTypeService, MapperFacade orikaMapperFacade) {
        this.notificationTypeService = notificationTypeService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/create")
    public NotificationTypeDto createNotificationType(@RequestBody NotificationTypeDto type) {
        NotificationType notificationType = notificationTypeService
                .createNotificationType(orikaMapperFacade.map(type, NotificationType.class));

        return orikaMapperFacade.map(notificationType, NotificationTypeDto.class);
    }

    @GetMapping("/all")
    public Set<NotificationTypeDto> getAllNotificationTypes() {
        Set<NotificationType> notificationTypes = notificationTypeService.getAllNotificationTypes();

        return orikaMapperFacade.mapAsSet(notificationTypes, NotificationTypeDto.class);
    }

}
