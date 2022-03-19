package com.zak.application.service.adapter;

import com.zak.application.service.api.EventTypeService;
import com.zak.domain.exception.EventTypeException;
import com.zak.domain.model.EventType;
import com.zak.domain.spi.EventTypePersistencePort;
import com.zak.domain.spi.IdGererator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class EventTypeServiceAdapter implements EventTypeService {

    private final EventTypePersistencePort eventTypePersistencePort;

    @Autowired
    private IdGererator idGererator;

    @Autowired
    public EventTypeServiceAdapter(EventTypePersistencePort eventTypePersistencePort) {
        this.eventTypePersistencePort = eventTypePersistencePort;
    }

    @Override
    public Optional<EventType> createEventType(EventType eventType) {
        eventType.setEventTypeId(idGererator.generateUniqueId());
        return eventTypePersistencePort.createEventType(eventType);
    }

    @Override
    public Optional<EventType> getEventTypeByEventTypeId(String eventTypeId) {
        Optional<EventType> type = eventTypePersistencePort.getEventTypeByEventTypeId(eventTypeId);
        if (!type.isPresent()) {
            throw new EventTypeException();
        }
        return type;
    }

    @Override
    public Optional<EventType> getEventTypeByEventTypeCode(String eventTypeCode) {
        Optional<EventType> type = eventTypePersistencePort.getEventTypeByEventTypeCode(eventTypeCode);
        if (!type.isPresent()) {
            throw new EventTypeException();
        }
        return type;
    }

    @Override
    public Optional<EventType> updateEventType(EventType eventType) {
        Optional<EventType> type = getEventTypeByEventTypeId(eventType.getEventTypeId());
        eventType.setId(type.get().getId());
        return eventTypePersistencePort.updateEventType(eventType);
    }

    @Override
    public Set<EventType> getAllEventTypes() {
        return eventTypePersistencePort.getAllEventTypes();
    }
}
