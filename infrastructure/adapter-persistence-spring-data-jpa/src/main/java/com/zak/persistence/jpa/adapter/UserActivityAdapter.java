package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.*;
import com.zak.domain.spi.UserActivityPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.UserActivityRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class UserActivityAdapter implements UserActivityPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final UserActivityRepository userActivityRepository;

    @Autowired
    public UserActivityAdapter(MapperFacade orikaMapperFacade,
                               UserActivityRepository userActivityRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.userActivityRepository = userActivityRepository;
    }

    @Override
    public Optional<UserActivity> createUserActivity(UserActivity userActivity) {
        UserActivityEntity userActivityEntity = orikaMapperFacade.map(userActivity, UserActivityEntity.class);
        return Optional.of(orikaMapperFacade.map(userActivityRepository.save(userActivityEntity), UserActivity.class));
    }

    @Override
    public Set<UserActivity> findUserActivitiesByUser(User user) {
        return orikaMapperFacade.mapAsSet(
                userActivityRepository.findByUser(orikaMapperFacade.map(user, UserEntity.class))
                , UserActivity.class);
    }
}
