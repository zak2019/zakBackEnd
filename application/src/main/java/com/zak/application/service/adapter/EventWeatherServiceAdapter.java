package com.zak.application.service.adapter;

import com.zak.application.service.api.EventWeatherService;
import com.zak.domain.exception.EventException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.Event;
import com.zak.domain.model.EventWeather;
import com.zak.domain.model.User;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class EventWeatherServiceAdapter implements EventWeatherService {

    private final EventPersistencePort eventPersistencePort;
    private final EventWeatherPersistencePort eventWeatherPersistencePort;
    private final UserPersistencePort userPersistencePort;


    @Autowired
    private IdGererator idGererator;

    @Autowired
    public EventWeatherServiceAdapter(EventPersistencePort eventPersistencePort,
                                      EventWeatherPersistencePort eventWeatherPersistencePort,
                                      UserPersistencePort userPersistencePort) {
        this.eventPersistencePort = eventPersistencePort;
        this.eventWeatherPersistencePort = eventWeatherPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public Optional<EventWeather> createEventWeather(String userId, String eventId, EventWeather weather) {

        Optional<EventWeather> savedWeather = null;

        Optional<User> user = userPersistencePort.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        if (!event.isPresent()) {
            throw new EventException("Event not found");
        }

        Optional<EventWeather> eventWeather =
                eventWeatherPersistencePort.findByEventAndUser(event.get(), user.get());
        if (eventWeather.isPresent()) {
            eventWeather.get().setRainy(weather.isRainy());
            eventWeather.get().setStorm(weather.isStorm());
            eventWeather.get().setSunnyCloud(weather.isSunnyCloud());
            eventWeather.get().setSunnyClear(weather.isSunnyClear());
            eventWeather.get().setEvent(event.get());
            savedWeather = updateEventWeather(eventWeather.get());
        } else {
            weather.setEventWeatherId(idGererator.generateUniqueId());
            weather.setCreationDate(new Date());
            weather.setEvent(event.get());
            weather.setUser(user.get());
            savedWeather = eventWeatherPersistencePort.createEventWeather(weather);
        }
        return savedWeather;
    }

    @Override
    public Optional<EventWeather> updateEventWeather(EventWeather weather) {
        return eventWeatherPersistencePort.updateEventWeather(weather);
    }

    @Override
    public Set<EventWeather> getEventsWeatherByEventId(String eventId) {

        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        if (!event.isPresent()) {
            throw new EventException("Event not found");
        }


        return eventWeatherPersistencePort.findByEvent(event.get());
    }
}
