package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.RetroSprintWeatherService;
import com.zak.domain.exception.RetroSprintException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class RetroSprintWeatherServiceAdapter implements RetroSprintWeatherService {

    private final RetroSprintPersistencePort retroSprintPersistencePort;
    private final RetroSprintWeatherPersistencePort retroSprintWeatherPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final NotificationService notificationService;


    @Autowired
    private IdGererator idGererator;

    @Autowired
    public RetroSprintWeatherServiceAdapter(RetroSprintPersistencePort retroSprintPersistencePort,
                                            RetroSprintWeatherPersistencePort retroSprintWeatherPersistencePort,
                                            UserPersistencePort userPersistencePort,
                                            NotificationService notificationService) {
        this.retroSprintPersistencePort = retroSprintPersistencePort;
        this.retroSprintWeatherPersistencePort = retroSprintWeatherPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.notificationService = notificationService;
    }


    @Transactional
    @Override
    public Optional<RetroSprintWeather> createRetroSprintWeather(String userId, String retroSprintId, RetroSprintWeather weather) {

        Optional<RetroSprintWeather> savedWeather = null;

        Optional<User> user = userPersistencePort.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<RetroSprint> retroSprint = retroSprintPersistencePort.findByRetroSprintId(retroSprintId);
        if (!retroSprint.isPresent()) {
            throw new RetroSprintException("Retro sprint not found");
        }

        Optional<RetroSprintWeather> retroSprintWeather =
                retroSprintWeatherPersistencePort.findByRetroSprintAndUser(retroSprint.get(), user.get());
        if (retroSprintWeather.isPresent()) {
            RetroSprintWeather existing = retroSprintWeather.get();
            existing.setRainy(weather.isRainy());
            existing.setStorm(weather.isStorm());
            existing.setSunnyCloud(weather.isSunnyCloud());
            existing.setSunnyClear(weather.isSunnyClear());
            existing.setRetroSprint(retroSprint.get());
            savedWeather = updateRetroSprintWeather(existing);
        } else {
            weather.setRetroSprintWeatherId(idGererator.generateUniqueId());
            weather.setCreationDate(new Date());
            weather.setRetroSprint(retroSprint.get());
            weather.setUser(user.get());
            savedWeather = retroSprintWeatherPersistencePort.createRetroSprintWeather(weather);
        }

        sendSprintWeatherUpdatedNotification(savedWeather.get(), user.get(), retroSprint.get());

        return savedWeather;
    }

    private void sendSprintWeatherUpdatedNotification(RetroSprintWeather savedWeather, User user, RetroSprint retroSprint) {
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        savedWeather.setRetroSprint(new RetroSprint(retroSprint.getRetroSprintId()));
        invitedUsers.forEach( invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(user.getUserId())) {
                notificationService.sendSprintWeatherNotificationToUser(invitedUserId, savedWeather);
            }
        });
    }

    @Override
    public Optional<RetroSprintWeather> updateRetroSprintWeather(RetroSprintWeather weather) {
        return retroSprintWeatherPersistencePort.updateRetroSprintWeather(weather);
    }

    @Override
    public Set<RetroSprintWeather> getRetroSprintsWeatherByRetroSprintId(String retroSprintId) {

        Optional<RetroSprint> retroSprint = retroSprintPersistencePort.findByRetroSprintId(retroSprintId);
        if (!retroSprint.isPresent()) {
            throw new RetroSprintException("Retro sprint not found");
        }


        return retroSprintWeatherPersistencePort.findByRetroSprint(retroSprint.get());
    }
}
