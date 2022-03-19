package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventWeather;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.EventPersistencePort;
import com.zak.domain.spi.EventWeatherPersistencePort;
import com.zak.persistence.jpa.entity.EventEntity;
import com.zak.persistence.jpa.entity.EventWeatherEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.entity.UsersUsersAssociationEntity;
import com.zak.persistence.jpa.repository.EventRepository;
import com.zak.persistence.jpa.repository.EventWeatherRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class EventWeatherAdapter implements EventWeatherPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final EventWeatherRepository eventWeatherRepository;

    @Autowired
    public EventWeatherAdapter(MapperFacade orikaMapperFacade,
                               EventWeatherRepository eventWeatherRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.eventWeatherRepository = eventWeatherRepository;
    }


    @Override
    public Optional<EventWeather> createEventWeather(EventWeather weather) {
        EventWeatherEntity weatherEntity = orikaMapperFacade.map(weather, EventWeatherEntity.class);
        return Optional.of(orikaMapperFacade.map(eventWeatherRepository.save(weatherEntity), EventWeather.class));
    }

    @Override
    public Optional<EventWeather> updateEventWeather(EventWeather weather) {
        EventWeatherEntity weatherEntity = orikaMapperFacade.map(weather, EventWeatherEntity.class);
        return Optional.of(orikaMapperFacade.map(eventWeatherRepository.save(weatherEntity), EventWeather.class));
    }

    @Override
    public Set<EventWeather> findByEvent(Event event) {
        EventEntity eventEntity = orikaMapperFacade.map(event, EventEntity.class);
        return orikaMapperFacade.mapAsSet(eventWeatherRepository.findByEvent(eventEntity), EventWeather.class);
    }

    @Override
    public Optional<EventWeather> findByEventAndUser(Event event, User user) {
        EventEntity eventEntity = orikaMapperFacade.map(event, EventEntity.class);
        UserEntity userEntity = orikaMapperFacade.map(user, UserEntity.class);
        Optional<EventWeatherEntity> weather = eventWeatherRepository.findByEventAndUser(eventEntity, userEntity);
        return weather.isPresent() ?
                Optional.of(orikaMapperFacade.map(weather.get(), EventWeather.class)) :
                Optional.empty();
    }
}
