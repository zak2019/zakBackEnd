package com.zak.application.service.api;

import com.zak.domain.model.User;
import com.zak.domain.model.UserActivity;

import java.util.Optional;
import java.util.Set;

public interface UserActivityService {
    Optional<UserActivity> createUserActivity(String userId, UserActivity userActivity);
    Optional<UserActivity> createUserActivity(User user, UserActivity userActivity);
    Set<UserActivity> getUserActivitiesByUserId(String userId);
}
