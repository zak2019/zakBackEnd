package com.zak.application.service.config;

import com.zak.application.service.adapter.*;
import com.zak.application.service.api.*;
import com.zak.domain.spi.*;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ProductService getProductService(ProductPersistencePort productPersistencePort) {
        return new ProductServiceAdapter(productPersistencePort);
    }

    @Bean
    public ConfirmationTokenService getConfirmationTokenService(
            ConfirmationTokenPersistencePort confirmationTokenPersistencePort) {
        return new ConfirmationTokenServiceAdapter(confirmationTokenPersistencePort);
    }

    @Bean
    public UserService getUserService(UserPersistencePort userPersistencePort,
                                      ConfirmationTokenService confirmationTokenService,
                                      EmailService emailService,
                                      UserRoleService userRoleService,
                                      UserRolePersistencePort userRolePersistencePort,
                                      UsersUsersAssociationService usersUsersAssociationService,
                                      AccountPersistencePort accountPersistencePort) {
        return new UserServiceAdapter(
                userPersistencePort,
                confirmationTokenService,
                emailService,
                userRoleService,
                userRolePersistencePort,
                usersUsersAssociationService,
                accountPersistencePort
        );
    }

    @Bean
    public UserRoleService getUserRoleService(UserRolePersistencePort userRolePersistencePort) {
        return new UserRoleServiceAdapter(userRolePersistencePort);
    }

    @Bean
    public TeamService getTeamService(TeamPersistencePort groupPersistencePort,
                                      UserService userService,
                                      AccountService accountService,
                                      EmailService emailService,
                                      NotificationService notificationService) {
        return new TeamServiceAdapter(groupPersistencePort, userService, accountService, emailService, notificationService);
    }

    @Bean
    public AccountService getAccountService(AccountPersistencePort accountPersistencePort,
                                            UserService userService,
                                            UsersUsersAssociationService usersUsersAssociationService,
                                            ConfirmationTokenService confirmationTokenService,
                                            EmailService emailService) {
        return new AccountServiceAdapter(accountPersistencePort, userService,
                usersUsersAssociationService, confirmationTokenService, emailService);
    }

    @Bean
    public EventService getEventService(EventPersistencePort eventPersistencePort,
                                        TeamPersistencePort teamPersistencePort,
                                        AccountService accountService,
                                        UserService userService,
                                        EmailService emailService,
                                        EventTypeService eventTypeService,
                                        NotificationService notificationService) {
        return new EventServiceAdapter(eventPersistencePort, teamPersistencePort,
                accountService, userService, emailService, eventTypeService, notificationService);
    }

    @Bean
    public RetroSprintService getRetroSprintService(RetroSprintPersistencePort retroSprintPersistencePort,
                                                    RetroSprintCommentGroupService retroSprintCommentGroupService,
                                                    EventService eventService,
                                                    TeamService teamService,
                                                    UserService userService,
                                                    EmailService emailService,
                                                    NotificationService NotificationService) {
        return new RetroSprintServiceAdapter(retroSprintPersistencePort, retroSprintCommentGroupService,
                eventService, teamService, userService, emailService, NotificationService);
    }

    @Bean
    public EventWeatherService getEventWeatherService(EventPersistencePort eventPersistencePort,
                                                      EventWeatherPersistencePort eventWeatherPersistencePort,
                                                      UserPersistencePort userPersistencePort) {
        return new EventWeatherServiceAdapter(eventPersistencePort, eventWeatherPersistencePort, userPersistencePort);
    }

    @Bean
    public RetroSprintWeatherService getRetroSprintWeatherService(RetroSprintPersistencePort retroSprintPersistencePort,
                                                                  RetroSprintWeatherPersistencePort retroSprintWeatherPersistencePort,
                                                                  UserPersistencePort userPersistencePort,
                                                                  NotificationService notificationService) {
        return new RetroSprintWeatherServiceAdapter(retroSprintPersistencePort,
                retroSprintWeatherPersistencePort,
                userPersistencePort, notificationService);
    }

    @Bean
    public RetroSprintCommentService getRetroSprintCommentService(
            RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
            RetroSprintCommentPersistencePort retroSprintCommentPersistencePort,
            UserPersistencePort userPersistencePort, NotificationService notificationService,
            RetroSprintService retroSprintService,IdGererator idGererator) {
        return new RetroSprintCommentServiceAdapter(retroSprintCommentGroupPersistencePort,
                retroSprintCommentPersistencePort, userPersistencePort, notificationService, retroSprintService, idGererator);
    }

    @Bean
    public RetroSprintCommentGroupVoteService getRetroSprintCommentGroupVoteService(
            RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
            RetroSprintCommentGroupVotePersistencePort retroSprintCommentGroupVotePersistencePort,
            UserPersistencePort userPersistencePort, RetroSprintService retroSprintService,
            NotificationService notificationService) {
        return new RetroSprintCommentGroupVoteServiceAdapter(retroSprintCommentGroupPersistencePort,
                retroSprintCommentGroupVotePersistencePort, userPersistencePort,
                retroSprintService, notificationService);
    }

    @Bean
    public RetroSprintCommentVoteService getRetroSprintCommentVoteService(
            RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
            RetroSprintCommentPersistencePort retroSprintCommentPersistencePort,
            RetroSprintCommentVotePersistencePort retroSprintCommentVotePersistencePort,
            UserPersistencePort userPersistencePort,
            RetroSprintService retroSprintService,
            NotificationService notificationService) {
        return new RetroSprintCommentVoteServiceAdapter(
                retroSprintCommentGroupPersistencePort, retroSprintCommentPersistencePort,
                retroSprintCommentVotePersistencePort, userPersistencePort,
                retroSprintService, notificationService);
    }

    @Bean
    public RetroSprintCommentGroupService getRetroSprintCommentGroupService(
            RetroSprintPersistencePort retroSprintPersistencePort,
            RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
            NotificationService notificationService, UserService userService, IdGererator idGererator) {
        return new RetroSprintCommentGroupServiceAdapter(retroSprintPersistencePort,
                retroSprintCommentGroupPersistencePort, notificationService, userService, idGererator);
    }

    @Bean
    public EventCommentService getEventCommentService(EventPersistencePort eventPersistencePort,
                                                      EventCommentPersistencePort eventCommentPersistencePort,
                                                      UserPersistencePort userPersistencePort,
                                                      UserActivityService userActivityService, IdGererator idGererator) {
        return new EventCommentServiceAdapter(
                eventPersistencePort,
                eventCommentPersistencePort,
                userPersistencePort,
                userActivityService,
                idGererator);
    }

    @Bean
    public EventTypeService getEventTypeService(EventTypePersistencePort eventTypePersistencePort) {
        return new EventTypeServiceAdapter(eventTypePersistencePort);
    }

    @Bean
    public UsersUsersAssociationService getUsersUsersAssociationService(
            UsersUsersAssociationPersistencePort usersUsersAssociationPersistencePort,
            UserPersistencePort userPersistencePort,
            UserRoleService userRoleService) {
        return new UsersUsersAssociationServiceAdapter(
                usersUsersAssociationPersistencePort,
                userPersistencePort,
                userRoleService
        );
    }

    @Bean
    public TaskService getTaskService(TaskPersistencePort taskPersistencePort,
                                      UserService userService,
                                      TeamService teamService,
                                      AccountService accountService,
                                      IdGererator idGererator) {
        return new TaskServiceAdapter(
                taskPersistencePort,
                userService,
                teamService,
                accountService,
                idGererator);
    }


    @Bean
    public UserActivityService getUserActivityService(UserActivityPersistencePort userActivityPersistencePort,
                                                      UserService userService,
                                                      IdGererator idGererator) {
        return new UserActivityServiceAdapter(
                userActivityPersistencePort,
                userService,
                idGererator);
    }

    @Bean
    public NotificationService getNotificationService(NotificationPersistencePort notificationPersistencePort,
                                                      NotificationTypeService notificationTypeService,
                                                      UserService userService) {
        return new NotificationServiceAdapter(notificationPersistencePort, notificationTypeService, userService);
    }

    @Bean
    public NotificationTypeService getNotificationTypeService(NotificationTypePersistencePort notificationTypePersistencePort) {
        return new NotificationTypeServiceAdapter(notificationTypePersistencePort);
    }
}
