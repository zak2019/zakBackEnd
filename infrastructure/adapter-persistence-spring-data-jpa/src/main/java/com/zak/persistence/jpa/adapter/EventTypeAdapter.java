package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.EventType;
import com.zak.domain.spi.EventTypePersistencePort;
import com.zak.persistence.jpa.entity.EventTypeEntity;
import com.zak.persistence.jpa.repository.EventTypeRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class EventTypeAdapter implements EventTypePersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeAdapter(MapperFacade orikaMapperFacade,
                            EventTypeRepository eventTypeRepository) {
        this.orikaMapperFacade = orikaMapperFacade;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public Optional<EventType> createEventType(EventType eventType) {
        EventTypeEntity eventTypeEntity = orikaMapperFacade.map(eventType, EventTypeEntity.class);
        return Optional.of(orikaMapperFacade.map(eventTypeRepository.save(eventTypeEntity), EventType.class));
    }

    @Override
    public Optional<EventType> updateEventType(EventType eventType) {
        EventTypeEntity eventTypeEntity = orikaMapperFacade.map(eventType, EventTypeEntity.class);
        return Optional.of(orikaMapperFacade.map(eventTypeRepository.save(eventTypeEntity), EventType.class));
    }

    @Override
    public Optional<EventType> getEventTypeByEventTypeId(String eventTypeId) {

        return Optional.of(orikaMapperFacade.map(eventTypeRepository.findByEventTypeId(eventTypeId).get(), EventType.class));
    }

    @Override
    public Optional<EventType> getEventTypeByEventTypeCode(String eventTypeCode) {

        return Optional.of(orikaMapperFacade.map(eventTypeRepository.findByCode(eventTypeCode).get(), EventType.class));
    }

    @Override
    public Set<EventType> getAllEventTypes() {
        return orikaMapperFacade.mapAsSet(eventTypeRepository.findAll(), EventType.class);
    }
}
