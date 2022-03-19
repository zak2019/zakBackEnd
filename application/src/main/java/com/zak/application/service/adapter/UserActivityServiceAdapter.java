package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.UserActivityPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class UserActivityServiceAdapter implements UserActivityService {

    private final UserActivityPersistencePort userActivityPersistencePort;
    private final UserService userService;
    private final IdGererator idGererator;


    @Autowired
    public UserActivityServiceAdapter(UserActivityPersistencePort userActivityPersistencePort,
                                      UserService userService,
                                      IdGererator idGererator) {
        this.userActivityPersistencePort = userActivityPersistencePort;
        this.userService = userService;
        this.idGererator = idGererator;
    }


    @Override
    public Optional<UserActivity> createUserActivity(String userId, UserActivity userActivity) {

        User user = getUser(userId);

        return setAndSaveUserActivityData(userActivity, user);
    }

    @Override
    public Optional<UserActivity> createUserActivity(User user, UserActivity userActivity) {


        return setAndSaveUserActivityData(userActivity, user);
    }

    private Optional<UserActivity> setAndSaveUserActivityData(UserActivity userActivity, User user) {
        userActivity.setUserActivityId(idGererator.generateUniqueId());
        userActivity.setCreationDate(new Date());
        userActivity.setUser(user);

        return userActivityPersistencePort.createUserActivity(userActivity);
    }



    private User getUser(String userId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }


    @Override
    public Set<UserActivity> getUserActivitiesByUserId(String userId) {
        User user = getUser(userId);

        return userActivityPersistencePort.findUserActivitiesByUser(user);

    }
}
