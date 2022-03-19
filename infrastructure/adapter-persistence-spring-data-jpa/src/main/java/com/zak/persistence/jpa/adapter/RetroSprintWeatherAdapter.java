package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.*;
import com.zak.domain.spi.RetroSprintWeatherPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.RetroSprintWeatherRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class RetroSprintWeatherAdapter implements RetroSprintWeatherPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintWeatherRepository retroSprintWeatherRepository;

    @Autowired
    public RetroSprintWeatherAdapter(MapperFacade orikaMapperFacade,
                                     RetroSprintWeatherRepository retroSprintWeatherRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.retroSprintWeatherRepository = retroSprintWeatherRepository;
    }

    @Override
    public Optional<RetroSprintWeather> createRetroSprintWeather(RetroSprintWeather weather) {
        RetroSprintWeatherEntity weatherEntity = orikaMapperFacade.map(weather, RetroSprintWeatherEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintWeatherRepository.save(weatherEntity), RetroSprintWeather.class));
    }

    @Override
    public Optional<RetroSprintWeather> updateRetroSprintWeather(RetroSprintWeather weather) {
        RetroSprintWeatherEntity weatherEntity = orikaMapperFacade.map(weather, RetroSprintWeatherEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintWeatherRepository.save(weatherEntity), RetroSprintWeather.class));
    }

    @Override
    public Set<RetroSprintWeather> findByRetroSprint(RetroSprint retroSprint) {
        RetroSprintEntity retroSprintEntity = orikaMapperFacade.map(retroSprint, RetroSprintEntity.class);
        return orikaMapperFacade
                .mapAsSet(retroSprintWeatherRepository.findByRetroSprint(retroSprintEntity), RetroSprintWeather.class);
    }

    @Override
    public Optional<RetroSprintWeather> findByRetroSprintAndUser(RetroSprint retroSprint, User user) {
        RetroSprintEntity retroSprintEntity = orikaMapperFacade.map(retroSprint, RetroSprintEntity.class);
        UserEntity userEntity = orikaMapperFacade.map(user, UserEntity.class);
        Optional<RetroSprintWeatherEntity> weather = retroSprintWeatherRepository
                .findByRetroSprintAndUser(retroSprintEntity, userEntity);
        return weather.isPresent() ?
                Optional.of(orikaMapperFacade.map(weather.get(), RetroSprintWeather.class)) :
                Optional.empty();
    }
}
