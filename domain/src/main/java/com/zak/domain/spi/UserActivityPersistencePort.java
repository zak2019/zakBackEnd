package com.zak.domain.spi;

import com.zak.domain.model.*;

import java.util.Optional;
import java.util.Set;

public interface UserActivityPersistencePort {

    Optional<UserActivity> createUserActivity(UserActivity userActivity)   ;
    Set<UserActivity> findUserActivitiesByUser(User user);
}
