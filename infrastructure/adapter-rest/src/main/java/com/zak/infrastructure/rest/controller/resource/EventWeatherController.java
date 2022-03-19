package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.EventWeatherService;
import com.zak.domain.model.Event;
import com.zak.domain.model.EventWeather;
import com.zak.infrastructure.rest.controller.resource.dto.EventWeatherDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/event-weather")
public class EventWeatherController {

    private final EventWeatherService eventWeatherService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public EventWeatherController(EventWeatherService eventWeatherService,
                                  MapperFacade orikaMapperFacade) {
        this.eventWeatherService = eventWeatherService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/event/{eventId}/user/{userId}")
    public EventWeatherDto createEventWeather(
            @PathVariable String eventId,
            @PathVariable String userId,
            @RequestBody EventWeatherDto eventWeatherDto) {
        Optional<EventWeather> savedEventWeather =
                eventWeatherService.createEventWeather(
                        userId,
                        eventId,
                        orikaMapperFacade.map(eventWeatherDto, EventWeather.class));
        return orikaMapperFacade.map(savedEventWeather.get(), EventWeatherDto.class);
    }

    @PostMapping("/update")
    public EventWeatherDto updateEventWeather(@RequestBody EventWeatherDto eventWeatherDto) {
        Optional<EventWeather> savedEventWeather =
                eventWeatherService.updateEventWeather(
                        orikaMapperFacade.map(eventWeatherDto, EventWeather.class));
        return orikaMapperFacade.map(savedEventWeather.get(), EventWeatherDto.class);
    }

    @GetMapping("/event/{eventId}")
    public Set<EventWeatherDto> getEventsWeatherByEventId( @PathVariable String eventId) {
        Set<EventWeather> eventsWeatherSet =
                eventWeatherService.getEventsWeatherByEventId(eventId);
        return orikaMapperFacade.mapAsSet(eventsWeatherSet, EventWeatherDto.class);
    }
}
