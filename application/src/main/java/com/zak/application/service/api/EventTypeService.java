package com.zak.application.service.api;

import com.zak.domain.model.EventType;
import java.util.Optional;
import java.util.Set;

public interface EventTypeService {

    Optional<EventType> createEventType(EventType eventType);
    Optional<EventType> updateEventType(EventType eventType);
    Optional<EventType> getEventTypeByEventTypeId(String eventTypeId);
    Optional<EventType> getEventTypeByEventTypeCode(String eventTypeCode);
    Set<EventType> getAllEventTypes();

}
