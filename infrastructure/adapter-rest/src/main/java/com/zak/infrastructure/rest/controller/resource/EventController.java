package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.EventService;
import com.zak.application.service.api.UserService;
import com.zak.domain.model.Event;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.Criteria;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.EventDto;
import com.zak.infrastructure.rest.controller.resource.dto.InviteUserRequestDto;
import com.zak.infrastructure.rest.controller.resource.dto.UserDto;
import com.zak.infrastructure.rest.controller.resource.dto.UsersUsersAssociationDto;
import com.zak.infrastructure.rest.controller.resource.util.MultipleEventDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/event")
public class EventController {

    private final EventService eventService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public EventController(EventService eventService, MapperFacade orikaMapperFacade) {
        this.eventService = eventService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/{eventId}")
    public EventDto getEventByEventId(@PathVariable String eventId) {
        Event event = eventService.getEventByEventId(eventId);

        return orikaMapperFacade.map(event, EventDto.class);
    }

    @GetMapping("/team/{teamId}/eventType/{eventType}/page/{page}/size/{size}")
    public PageGenerics<EventDto>getComingEventByTeamIdAndEventType(@PathVariable String teamId,
                                                       @PathVariable String eventType,
                                                       @PathVariable int page,
                                                       @PathVariable int size) {
        PageGenerics<Event> eventPage = eventService.getComingEventByTeamIdAndEventType(teamId, eventType, page, size);

        PageGenerics<EventDto> pageOfComingEvent =
                new PageGenerics<EventDto>();

        pageOfComingEvent.setPageable(new Criteria(page, size));
        pageOfComingEvent.setFirst(eventPage.getActualPage() == 0);
        pageOfComingEvent
                .setLast(eventPage.getActualPage() + 1 ==
                        eventPage.getTotalPages());

        pageOfComingEvent.setData(
                orikaMapperFacade.mapAsSet(
                        eventPage.getData(),
                        EventDto.class));
        pageOfComingEvent.setSize(eventPage.getSize());
        pageOfComingEvent.setActualPage(eventPage.getActualPage());
        pageOfComingEvent.setTotalPages(eventPage.getTotalPages());
        pageOfComingEvent.setTotalData(eventPage.getTotalData());

        return pageOfComingEvent;
    }

    @GetMapping("/{eventType}/team/{teamId}")
    public Set<EventDto> getTodayEventsByTeamIdAndEventType(@PathVariable String teamId, @PathVariable String eventType) {
        Set<Event> events = eventService.getTodayEventsByTeamIdAndEventType(teamId, eventType);

        return orikaMapperFacade.mapAsSet(events, EventDto.class);
    }


    @PostMapping("/createEvents/account/{accountId}")
    public ResponseContent<Set<EventDto>> createMultipleEvent(@PathVariable String accountId,
                                                              @RequestBody MultipleEventDto multipleEventDto) {

        Set<Event> savedEvents = eventService.createMultipleEvent(
                accountId,
                orikaMapperFacade.map(multipleEventDto.getEvent(), Event.class),
                multipleEventDto.getDateList(),
                multipleEventDto.getEventHour(),
                multipleEventDto.getEventEndHour());
        return new ResponseContent<Set<EventDto>>(
                orikaMapperFacade.mapAsSet(savedEvents, EventDto.class),
                "Events Created"
        );
    }

    @PostMapping("/account/{accountId}")
    public ResponseContent<EventDto> createEvent(@PathVariable String accountId,
                                                 @RequestBody EventDto eventDto) {
        Optional<Event> savedEvent = eventService.createEvent(accountId, orikaMapperFacade.map(eventDto, Event.class));
        return new ResponseContent<EventDto>(
                orikaMapperFacade.map(savedEvent.get(),
                        EventDto.class), "Event Created"
        );
    }

    @PostMapping
    public ResponseContent<EventDto> updateEvent(@RequestBody EventDto eventDto) {
        Optional<Event> updatedEvent = eventService.updateEvent(orikaMapperFacade.map(eventDto, Event.class));
        return new ResponseContent<>(
                orikaMapperFacade.map(updatedEvent.get(),
                        EventDto.class), "Event updated"
        );
    }

    @PostMapping("/{eventId}/delete")
    public ResponseEntity<?> deleteEvent(@PathVariable String eventId) {
        eventService.deleteEventByEventId(eventId);
        return ResponseEntity.ok(new MessageResponseEntity("Event deleted successfully"));
    }


    @GetMapping("/account/{accountId}/user/{userId}/criteria")
    PageGenerics<EventDto> getPageOfEventsByAccountIdAndUserId(
            @PathVariable String accountId,
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEvents =
                eventService.getPageOfEventsByAccountByInvitedUser(
                        accountId,
                        userId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsByAccountAndUser =
                new PageGenerics<EventDto>();

        pageOfEventsByAccountAndUser.setPageable(new Criteria(page, size));
        pageOfEventsByAccountAndUser.setFirst(pageOfEvents.getActualPage() == 0);
        pageOfEventsByAccountAndUser
                .setLast(pageOfEvents.getActualPage() + 1 ==
                        pageOfEvents.getTotalPages());

        pageOfEventsByAccountAndUser.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEvents.getData(),
                        EventDto.class));
        pageOfEventsByAccountAndUser.setSize(pageOfEvents.getSize());
        pageOfEventsByAccountAndUser.setActualPage(pageOfEvents.getActualPage());
        pageOfEventsByAccountAndUser.setTotalPages(pageOfEvents.getTotalPages());
        pageOfEventsByAccountAndUser.setTotalData(pageOfEvents.getTotalData());

        return pageOfEventsByAccountAndUser;
    }

    @GetMapping("/account/{accountId}/user/{userId}/previousStartingFrom/{eventId}/criteria")
    PageGenerics<EventDto> getPageOfPreviousEventsByAccountIdAndUserIdAndStartDate(
            @PathVariable String accountId,
            @PathVariable String userId,
            @PathVariable String eventId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEvents =
                eventService.getPageOfPreviousEventsByAccountByInvitedUserAndStartingFrom(
                        accountId,
                        userId,
                        eventId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsByAccountAndUserAndStartDate =
                new PageGenerics<EventDto>();

        pageOfEventsByAccountAndUserAndStartDate.setPageable(new Criteria(page, size));
        pageOfEventsByAccountAndUserAndStartDate.setFirst(pageOfEvents.getActualPage() == 0);
        pageOfEventsByAccountAndUserAndStartDate
                .setLast(pageOfEvents.getActualPage() + 1 ==
                        pageOfEvents.getTotalPages());

        pageOfEventsByAccountAndUserAndStartDate.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEvents.getData(),
                        EventDto.class));
        pageOfEventsByAccountAndUserAndStartDate.setSize(pageOfEvents.getSize());
        pageOfEventsByAccountAndUserAndStartDate.setActualPage(pageOfEvents.getActualPage());
        pageOfEventsByAccountAndUserAndStartDate.setTotalPages(pageOfEvents.getTotalPages());
        pageOfEventsByAccountAndUserAndStartDate.setTotalData(pageOfEvents.getTotalData());

        return pageOfEventsByAccountAndUserAndStartDate;
    }

    @GetMapping("/account/{accountId}/user/{userId}/toComeStartingFrom/{eventId}/criteria")
    PageGenerics<EventDto> getPageOfToComeEventsByAccountIdAndUserIdAndStartDate(
            @PathVariable String accountId,
            @PathVariable String userId,
            @PathVariable String eventId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEvents =
                eventService.getPageOfToComeEventsByAccountByInvitedUserAndStartingFrom(
                        accountId,
                        userId,
                        eventId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsByAccountAndUserAndStartDate =
                new PageGenerics<EventDto>();

        pageOfEventsByAccountAndUserAndStartDate.setPageable(new Criteria(page, size));
        pageOfEventsByAccountAndUserAndStartDate.setFirst(pageOfEvents.getActualPage() == 0);
        pageOfEventsByAccountAndUserAndStartDate
                .setLast(pageOfEvents.getActualPage() + 1 ==
                        pageOfEvents.getTotalPages());

        pageOfEventsByAccountAndUserAndStartDate.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEvents.getData(),
                        EventDto.class));
        pageOfEventsByAccountAndUserAndStartDate.setSize(pageOfEvents.getSize());
        pageOfEventsByAccountAndUserAndStartDate.setActualPage(pageOfEvents.getActualPage());
        pageOfEventsByAccountAndUserAndStartDate.setTotalPages(pageOfEvents.getTotalPages());
        pageOfEventsByAccountAndUserAndStartDate.setTotalData(pageOfEvents.getTotalData());

        return pageOfEventsByAccountAndUserAndStartDate;
    }


    @GetMapping("/account/{accountId}/user/{userId}/team/{teamId}/criteria")
    PageGenerics<EventDto> getPageOfEventsByAccountIdAndUserIdAndTeamId(
            @PathVariable String accountId,
            @PathVariable String userId,
            @PathVariable String teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEvents =
                eventService.getPageOfEventsByAccountByInvitedUserAndTeamId(
                        accountId,
                        userId,
                        teamId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsByAccountAndUserAndTeam =
                new PageGenerics<EventDto>();

        pageOfEventsByAccountAndUserAndTeam.setPageable(new Criteria(page, size));
        pageOfEventsByAccountAndUserAndTeam.setFirst(pageOfEvents.getActualPage() == 0);
        pageOfEventsByAccountAndUserAndTeam
                .setLast(pageOfEvents.getActualPage() + 1 ==
                        pageOfEvents.getTotalPages());

        pageOfEventsByAccountAndUserAndTeam.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEvents.getData(),
                        EventDto.class));
        pageOfEventsByAccountAndUserAndTeam.setSize(pageOfEvents.getSize());
        pageOfEventsByAccountAndUserAndTeam.setActualPage(pageOfEvents.getActualPage());
        pageOfEventsByAccountAndUserAndTeam.setTotalPages(pageOfEvents.getTotalPages());
        pageOfEventsByAccountAndUserAndTeam.setTotalData(pageOfEvents.getTotalData());

        return pageOfEventsByAccountAndUserAndTeam;
    }

    @GetMapping("/to-come/account/{accountId}/user/{userId}/criteria")
    PageGenerics<EventDto> getPageOfEventsToComeByAccountIdAndUserId(
            @PathVariable String accountId,
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEventsToCome =
                eventService.getPageOfEventsToComeByAccountByInvitedUser(
                        accountId,
                        userId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsToComeByAccountAndUser =
                new PageGenerics<EventDto>();

        pageOfEventsToComeByAccountAndUser.setPageable(new Criteria(page, size));
        pageOfEventsToComeByAccountAndUser.setFirst(pageOfEventsToCome.getActualPage() == 0);
        pageOfEventsToComeByAccountAndUser
                .setLast(pageOfEventsToCome.getActualPage() + 1 ==
                        pageOfEventsToCome.getTotalPages());

        pageOfEventsToComeByAccountAndUser.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEventsToCome.getData(),
                        EventDto.class));
        pageOfEventsToComeByAccountAndUser.setSize(pageOfEventsToCome.getSize());
        pageOfEventsToComeByAccountAndUser.setActualPage(pageOfEventsToCome.getActualPage());
        pageOfEventsToComeByAccountAndUser.setTotalPages(pageOfEventsToCome.getTotalPages());
        pageOfEventsToComeByAccountAndUser.setTotalData(pageOfEventsToCome.getTotalData());

        return pageOfEventsToComeByAccountAndUser;
    }

    @GetMapping("/to-come/account/{accountId}/criteria")
    PageGenerics<EventDto> getPageOfEventsToComeByAccountId(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEventsToCome =
                eventService.getPageOfEventsToComeByAccountId(
                        accountId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsToComeByAccount =
                new PageGenerics<EventDto>();

        pageOfEventsToComeByAccount.setPageable(new Criteria(page, size));
        pageOfEventsToComeByAccount.setFirst(pageOfEventsToCome.getActualPage() == 0);
        pageOfEventsToComeByAccount
                .setLast(pageOfEventsToCome.getActualPage() + 1 ==
                        pageOfEventsToCome.getTotalPages());

        pageOfEventsToComeByAccount.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEventsToCome.getData(),
                        EventDto.class));
        pageOfEventsToComeByAccount.setSize(pageOfEventsToCome.getSize());
        pageOfEventsToComeByAccount.setActualPage(pageOfEventsToCome.getActualPage());
        pageOfEventsToComeByAccount.setTotalPages(pageOfEventsToCome.getTotalPages());
        pageOfEventsToComeByAccount.setTotalData(pageOfEventsToCome.getTotalData());

        return pageOfEventsToComeByAccount;
    }

    @GetMapping("/to-come/account/{accountId}/team/{teamId}/criteria")
    PageGenerics<EventDto> getPageOfEventsToComeByAccountIdAndTeamId(
            @PathVariable String accountId,
            @PathVariable String teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEventsToCome =
                eventService.getPageOfEventsToComeByAccountIdAndTeamId(
                        accountId,
                        teamId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsToComeByAccountAndTeam =
                new PageGenerics<EventDto>();

        pageOfEventsToComeByAccountAndTeam.setPageable(new Criteria(page, size));
        pageOfEventsToComeByAccountAndTeam.setFirst(pageOfEventsToCome.getActualPage() == 0);
        pageOfEventsToComeByAccountAndTeam
                .setLast(pageOfEventsToCome.getActualPage() + 1 ==
                        pageOfEventsToCome.getTotalPages());

        pageOfEventsToComeByAccountAndTeam.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEventsToCome.getData(),
                        EventDto.class));
        pageOfEventsToComeByAccountAndTeam.setSize(pageOfEventsToCome.getSize());
        pageOfEventsToComeByAccountAndTeam.setActualPage(pageOfEventsToCome.getActualPage());
        pageOfEventsToComeByAccountAndTeam.setTotalPages(pageOfEventsToCome.getTotalPages());
        pageOfEventsToComeByAccountAndTeam.setTotalData(pageOfEventsToCome.getTotalData());

        return pageOfEventsToComeByAccountAndTeam;
    }


    @GetMapping("/to-come/account/{accountId}/user/{userId}/team/{teamId}/criteria")
    PageGenerics<EventDto> getPageOfEventsToComeByAccountIdAndUserIdAndTeamId(
            @PathVariable String accountId,
            @PathVariable String userId,
            @PathVariable String teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate") String sortBy) {
        PageGenerics<Event> pageOfEventsToCome =
                eventService.getPageOfEventsToComeByAccountByInvitedUserAndTeamId(
                        accountId,
                        userId,
                        teamId,
                        page,
                        size,
                        sortBy);

        PageGenerics<EventDto> pageOfEventsToComeByAccountAndUserAndTeam =
                new PageGenerics<EventDto>();

        pageOfEventsToComeByAccountAndUserAndTeam.setPageable(new Criteria(page, size));
        pageOfEventsToComeByAccountAndUserAndTeam.setFirst(pageOfEventsToCome.getActualPage() == 0);
        pageOfEventsToComeByAccountAndUserAndTeam
                .setLast(pageOfEventsToCome.getActualPage() + 1 ==
                        pageOfEventsToCome.getTotalPages());

        pageOfEventsToComeByAccountAndUserAndTeam.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfEventsToCome.getData(),
                        EventDto.class));
        pageOfEventsToComeByAccountAndUserAndTeam.setSize(pageOfEventsToCome.getSize());
        pageOfEventsToComeByAccountAndUserAndTeam.setActualPage(pageOfEventsToCome.getActualPage());
        pageOfEventsToComeByAccountAndUserAndTeam.setTotalPages(pageOfEventsToCome.getTotalPages());
        pageOfEventsToComeByAccountAndUserAndTeam.setTotalData(pageOfEventsToCome.getTotalData());

        return pageOfEventsToComeByAccountAndUserAndTeam;
    }


    @GetMapping("/account/{accountId}/team/{teamId}/dates")
    Set<EventDto> getEventsByDatesAndAccountIdAndTeamId(
            @PathVariable String accountId,
            @PathVariable String teamId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        Set<Event> events = eventService.getEventsByDatesAndAccountIdAndTeamId(
                accountId,
                teamId,
                startDate,
                endDate);

        return orikaMapperFacade.mapAsSet(events, EventDto.class);
    }
}
