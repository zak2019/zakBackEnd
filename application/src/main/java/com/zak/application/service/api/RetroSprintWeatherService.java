package com.zak.application.service.api;

import com.zak.domain.model.RetroSprintWeather;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintWeatherService {
    Optional<RetroSprintWeather> createRetroSprintWeather(String userId, String eventId, RetroSprintWeather weather);
    Optional<RetroSprintWeather> updateRetroSprintWeather(RetroSprintWeather weather);
    Set<RetroSprintWeather> getRetroSprintsWeatherByRetroSprintId(String retroSprintId);
}
