package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.EventCommentService;
import com.zak.application.service.api.EventWeatherService;
import com.zak.domain.model.EventComment;
import com.zak.domain.model.EventWeather;
import com.zak.infrastructure.rest.controller.resource.dto.EventCommentDto;
import com.zak.infrastructure.rest.controller.resource.dto.EventWeatherDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/event-comment")
public class EventCommentController {

    private final EventCommentService eventCommentService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public EventCommentController(EventCommentService eventCommentService,
                                  MapperFacade orikaMapperFacade) {
        this.eventCommentService = eventCommentService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/event/{eventId}/user/{userId}")
    public EventCommentDto createEventComment(
            @PathVariable String eventId,
            @PathVariable String userId,
            @RequestBody EventCommentDto eventCommentDto) {
        Optional<EventComment> savedEventComment =
                eventCommentService.createEventComment(
                        userId,
                        eventId,
                        orikaMapperFacade.map(eventCommentDto, EventComment.class));
        return orikaMapperFacade.map(savedEventComment.get(), EventCommentDto.class);
    }

    @PostMapping("/update")
    public EventCommentDto updateEventComment(@RequestBody EventCommentDto eventCommentDto) {
        Optional<EventComment> savedEventComment =
                eventCommentService.updateEventComment(
                        orikaMapperFacade.map(eventCommentDto, EventComment.class));
        return orikaMapperFacade.map(savedEventComment.get(), EventCommentDto.class);
    }

    @GetMapping("/event/{eventId}")
    public Set<EventCommentDto> getEventsCommentsByEventId( @PathVariable String eventId) {
        Set<EventComment> eventsCommentSet =
                eventCommentService.getEventsCommentsByEventId(eventId);
        return orikaMapperFacade.mapAsSet(eventsCommentSet, EventCommentDto.class);
    }

    @PostMapping("/{eventCommentId}/delete")
    public boolean deleteEventCommentByEventCommentId( @PathVariable String eventCommentId) {
        return eventCommentService.deleteEventCommentByEventCommentId(eventCommentId);
    }
}
