package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.EventService;
import com.zak.application.service.api.EventTypeService;
import com.zak.domain.model.Event;
import com.zak.domain.model.EventType;
import com.zak.domain.model.util.Criteria;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.EventDto;
import com.zak.infrastructure.rest.controller.resource.dto.EventTypeDto;
import com.zak.infrastructure.rest.controller.resource.util.MultipleEventDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/event-type")
public class EventTypeController {

    private final EventTypeService eventTypeService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService, MapperFacade orikaMapperFacade) {
        this.eventTypeService = eventTypeService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping()
    public EventTypeDto createEventType(@RequestBody EventTypeDto eventType) {
        Optional<EventType> savedType = eventTypeService.createEventType(orikaMapperFacade.map(eventType, EventType.class));
        return orikaMapperFacade.map(savedType.get(), EventTypeDto.class);
    }

    @PostMapping("/update")
    public EventTypeDto updateEventType(@RequestBody EventTypeDto eventType) {
        Optional<EventType> savedType = eventTypeService.updateEventType(orikaMapperFacade.map(eventType, EventType.class));
        return orikaMapperFacade.map(savedType.get(), EventTypeDto.class);
    }

    @GetMapping("/{eventTypeId}")
    public EventTypeDto getEventTypeByEventTypeId(@PathVariable String eventTypeId) {
        Optional<EventType> eventType = eventTypeService.getEventTypeByEventTypeId(eventTypeId);

        return orikaMapperFacade.map(eventType.get(), EventTypeDto.class);
    }

    @GetMapping("/code/{eventTypeCode}")
    public EventTypeDto getEventTypeByEventTypeCode(@PathVariable String eventTypeCode) {
        Optional<EventType> eventType = eventTypeService.getEventTypeByEventTypeCode(eventTypeCode);

        return orikaMapperFacade.map(eventType.get(), EventTypeDto.class);
    }

    @GetMapping("/all")
    public Set<EventTypeDto> getAllEventTypes() {
        Set<EventType> eventTypes = eventTypeService.getAllEventTypes();

        return orikaMapperFacade.mapAsSet(eventTypes, EventTypeDto.class);
    }

}
