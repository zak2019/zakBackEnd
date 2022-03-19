package com.zak.domain.spi;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventType;
import com.zak.domain.model.util.PageGenerics;

import java.util.Optional;
import java.util.Set;

public interface EventTypePersistencePort {

    Optional<EventType> createEventType(EventType eventType);
    Optional<EventType> updateEventType(EventType eventType);
    Optional<EventType> getEventTypeByEventTypeId(String eventTypeId);
    Optional<EventType> getEventTypeByEventTypeCode(String eventTypeCode);
    Set<EventType> getAllEventTypes();
}
