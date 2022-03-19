package com.zak.domain.spi;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventWeather;
import com.zak.domain.model.User;

import java.util.Optional;
import java.util.Set;

public interface EventWeatherPersistencePort {

    Optional<EventWeather> createEventWeather(EventWeather weather);
    Optional<EventWeather> updateEventWeather(EventWeather weather);
    Set<EventWeather> findByEvent(Event event);
    Optional<EventWeather> findByEventAndUser(Event event, User user);
}
