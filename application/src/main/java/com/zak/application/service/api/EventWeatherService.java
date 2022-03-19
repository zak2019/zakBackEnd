package com.zak.application.service.api;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventWeather;
import com.zak.domain.model.util.PageGenerics;

import java.util.Optional;
import java.util.Set;

public interface EventWeatherService {
    Optional<EventWeather> createEventWeather(String userId, String eventId, EventWeather weather);
    Optional<EventWeather> updateEventWeather(EventWeather weather);
    Set<EventWeather> getEventsWeatherByEventId(String eventId);
}
