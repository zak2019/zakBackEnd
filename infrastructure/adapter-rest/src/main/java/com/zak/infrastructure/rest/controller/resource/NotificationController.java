package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.NotificationService;
import com.zak.domain.model.Notification;
import com.zak.domain.model.util.Criteria;
import com.zak.domain.model.util.PageGenerics;
import com.zak.infrastructure.rest.controller.resource.dto.NotificationDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public NotificationController(NotificationService notificationService, MapperFacade orikaMapperFacade) {
        this.notificationService = notificationService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/{notificationId}/setConsulted")
    public NotificationDto setNotificationConsulted(@PathVariable String notificationId) {
        Notification notification = notificationService.setNotificationConsulted(notificationId);

        return orikaMapperFacade.map(notification, NotificationDto.class);
    }

    @PostMapping("/user/{userId}/setPageConsulted")
    public boolean setNotificationPageConsulted(@PathVariable String userId) {
        return notificationService.setNotificationPageConsulted(userId);
    }

    @GetMapping("/{notificationId}")
    public NotificationDto getNotificationByNotificationId(@PathVariable String notificationId) {
        Notification notification = notificationService.getNotificationByNotificationId(notificationId);

        return orikaMapperFacade.map(notification, NotificationDto.class);
    }

    @GetMapping("/userToNotify/{userId}/page/{page}/size/{size}")
    public PageGenerics<NotificationDto> getNotificationsByUserId(@PathVariable String userId,
                                                                  @PathVariable int page,
                                                                  @PathVariable int size) {
        PageGenerics<Notification> notificationPage = notificationService.getNotificationsByUserId(userId, page, size);

        PageGenerics<NotificationDto> pageOfAllNotifications =
                new PageGenerics<NotificationDto>();

        pageOfAllNotifications.setPageable(new Criteria(page, size));
        pageOfAllNotifications.setFirst(notificationPage.getActualPage() == 0);
        pageOfAllNotifications
                .setLast(notificationPage.getActualPage() + 1 ==
                        notificationPage.getTotalPages());

        Set<NotificationDto> data = orikaMapperFacade.mapAsSet(
                notificationPage.getData(),
                NotificationDto.class);
        pageOfAllNotifications.setData(data);
        pageOfAllNotifications.setSize(notificationPage.getSize());
        pageOfAllNotifications.setActualPage(notificationPage.getActualPage());
        pageOfAllNotifications.setTotalPages(notificationPage.getTotalPages());
        pageOfAllNotifications.setTotalData(notificationPage.getTotalData());

        return pageOfAllNotifications;
    }

    @GetMapping("/notConsulted/userToNotify/{userId}/page/{page}/size/{size}")
    public PageGenerics<NotificationDto> getNotConsultedNotificationsByUserId(@PathVariable String userId,
                                                                              @PathVariable int page,
                                                                              @PathVariable int size) {
        PageGenerics<Notification> notificationPage = notificationService.getNotConsultedNotificationsByUserId(userId, page, size);

        PageGenerics<NotificationDto> pageOfNotConsultedNotifications =
                new PageGenerics<NotificationDto>();

        pageOfNotConsultedNotifications.setPageable(new Criteria(page, size));
        pageOfNotConsultedNotifications.setFirst(notificationPage.getActualPage() == 0);
        pageOfNotConsultedNotifications
                .setLast(notificationPage.getActualPage() + 1 ==
                        notificationPage.getTotalPages());

        pageOfNotConsultedNotifications.setData(
                orikaMapperFacade.mapAsSet(
                        notificationPage.getData(),
                        NotificationDto.class));
        pageOfNotConsultedNotifications.setSize(notificationPage.getSize());
        pageOfNotConsultedNotifications.setActualPage(notificationPage.getActualPage());
        pageOfNotConsultedNotifications.setTotalPages(notificationPage.getTotalPages());
        pageOfNotConsultedNotifications.setTotalData(notificationPage.getTotalData());

        return pageOfNotConsultedNotifications;
    }
}
