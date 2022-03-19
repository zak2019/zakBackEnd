package com.zak.domain.spi;

import com.zak.domain.model.*;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintWeatherPersistencePort {

    Optional<RetroSprintWeather> createRetroSprintWeather(RetroSprintWeather weather);
    Optional<RetroSprintWeather> updateRetroSprintWeather(RetroSprintWeather weather);
    Set<RetroSprintWeather> findByRetroSprint(RetroSprint retroSprint);
    Optional<RetroSprintWeather> findByRetroSprintAndUser(RetroSprint retroSprint, User user);
}
