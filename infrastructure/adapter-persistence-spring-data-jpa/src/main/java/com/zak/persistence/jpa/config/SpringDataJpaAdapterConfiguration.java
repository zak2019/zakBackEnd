package com.zak.persistence.jpa.config;

import com.zak.domain.spi.*;
import com.zak.persistence.jpa.adapter.*;
import com.zak.persistence.jpa.repository.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDataJpaAdapterConfiguration {

    @Bean
    public ProductPersistencePort getProductPersistencPort(ProductRepository productRepository) {
        return new ProductSpringJpaAdapter(productRepository);
    }

    @Bean
    public UserPersistencePort getUserPersistencPort(UserRepository userRepository,
                                                     MapperFacade orikaMapperFacade) {
        return new UserAdapter(userRepository, orikaMapperFacade);
    }

    @Bean
    public AccountPersistencePort getAccountPersistencPort(AccountRepository accountRepository,
                                                     MapperFacade orikaMapperFacade) {
        return new AccountAdapter(accountRepository, orikaMapperFacade);
    }

    @Bean
    public EventPersistencePort getEventPersistencePort(MapperFacade orikaMapperFacade,
                                                           EventRepository eventRepository) {
        return new EventAdapter(orikaMapperFacade, eventRepository);
    }

    @Bean
    public RetroSprintPersistencePort getRetroSprintPersistencePort(MapperFacade orikaMapperFacade,
                                                       RetroSprintRepository retroSprintRepository) {
        return new RetroSprintAdapter(orikaMapperFacade, retroSprintRepository);
    }

    @Bean
    public EventWeatherPersistencePort getEventWeatherPersistencPort(MapperFacade orikaMapperFacade,
                                                           EventWeatherRepository eventWeatherRepository) {
        return new EventWeatherAdapter(orikaMapperFacade, eventWeatherRepository);
    }

    @Bean
    public RetroSprintWeatherPersistencePort getRetroSprintWeatherPersistencPort(MapperFacade orikaMapperFacade,
                                                           RetroSprintWeatherRepository retroSprintWeatherRepository) {
        return new RetroSprintWeatherAdapter(orikaMapperFacade, retroSprintWeatherRepository);
    }

    @Bean
    public EventCommentPersistencePort getEventCommentPersistencePort(MapperFacade orikaMapperFacade,
                                                           EventCommentRepository eventCommentRepository) {
        return new EventCommentAdapter(orikaMapperFacade, eventCommentRepository);
    }

    @Bean
    public RetroSprintCommentPersistencePort getRetroSprintCommentPersistencePort(MapperFacade orikaMapperFacade,
                                                                     RetroSprintCommentRepository retroSprintCommentRepository) {
        return new RetroSprintCommentAdapter(orikaMapperFacade, retroSprintCommentRepository);
    }

    @Bean
    public RetroSprintCommentGroupPersistencePort getRetroSprintCommentGroupPersistencePort(MapperFacade orikaMapperFacade,
                                                                     RetroSprintCommentGroupRepository retroSprintCommentGroupRepository) {
        return new RetroSprintCommentGroupAdapter(orikaMapperFacade, retroSprintCommentGroupRepository);
    }

    @Bean
    public RetroSprintCommentGroupVotePersistencePort getRetroSprintCommentGroupVotePersistencePort(MapperFacade orikaMapperFacade,
                                                                     RetroSprintCommentGroupVoteRepository retroSprintCommentGroupVoteRepository) {
        return new RetroSprintCommentGroupVoteAdapter(orikaMapperFacade, retroSprintCommentGroupVoteRepository);
    }

    @Bean
    public RetroSprintCommentVotePersistencePort getRetroSprintCommentVotePersistencePort(MapperFacade orikaMapperFacade,
                                                                                          RetroSprintCommentVoteRepository retroSprintCommentVoteRepository) {
        return new RetroSprintCommentVoteAdapter(orikaMapperFacade, retroSprintCommentVoteRepository);
    }

    @Bean
    public EventTypePersistencePort getEventTypePersistencPort(MapperFacade orikaMapperFacade,
                                                               EventTypeRepository eventTypeRepository) {
        return new EventTypeAdapter(orikaMapperFacade, eventTypeRepository);
    }

    @Bean
    public UserRolePersistencePort getUserRolePersistencPort(UserRoleRepository userRoleRepository) {
        return new UserRoleAdapter(userRoleRepository);
    }

    @Bean
    public TeamPersistencePort getTeamPersistencPort(TeamRepository teamRepository,
                                                      MapperFacade orikaMapperFacade) {
        return new TeamAdapter(teamRepository, orikaMapperFacade);
    }

    @Bean
    public ConfirmationTokenPersistencePort getConfirmationTokenPersistencPort(
            ConfirmationTokenRepository confirmationTokenRepository,
            MapperFacade orikaMapperFacade) {
        return new ConfirmationTokenAdapter(confirmationTokenRepository, orikaMapperFacade);
    }

    @Bean
    public UsersUsersAssociationPersistencePort getUsersUsersAssociationPersistencePort(
            MapperFacade orikaMapperFacade,
            UsersUsersAssociationRepository usersUsersAssociationRepository) {
        return new UsersUsersAssociationAdapter(orikaMapperFacade, usersUsersAssociationRepository);
    }

    @Bean
    public TaskPersistencePort getTaskPersistencePort(MapperFacade orikaMapperFacade,
                                                      TaskRepository taskRepository) {
        return new TaskAdapter(orikaMapperFacade, taskRepository);
    }

    @Bean
    public UserActivityPersistencePort getUserActivityPersistencePort(MapperFacade orikaMapperFacade,
                                                      UserActivityRepository userActivityRepository) {
        return new UserActivityAdapter(orikaMapperFacade, userActivityRepository);
    }

    @Bean
    public MapperFacade getMapperFacade() {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    @Bean
    public NotificationPersistencePort getNotificationPersistencePort(MapperFacade orikaMapperFacade,
                                                        NotificationRepository notificationRepository) {
        return new NotificationAdapter(orikaMapperFacade, notificationRepository);
    }

    @Bean
    public NotificationTypePersistencePort getNotificationTypePersistencPort(MapperFacade orikaMapperFacade,
                                                               NotificationTypeRepository notificationTypeRepository) {
        return new NotificationTypeAdapter(orikaMapperFacade, notificationTypeRepository);
    }
}
