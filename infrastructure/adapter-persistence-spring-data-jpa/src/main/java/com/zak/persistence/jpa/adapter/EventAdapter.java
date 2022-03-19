package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.EventPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.EventRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class EventAdapter implements EventPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final EventRepository eventRepository;

    @Autowired
    public EventAdapter(MapperFacade orikaMapperFacade,
                        EventRepository eventRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> createEvent(Event event) {
        EventEntity eventEntity = orikaMapperFacade.map(event, EventEntity.class);
        return Optional.of(orikaMapperFacade.map(eventRepository.save(eventEntity), Event.class));
    }

    @Override
    public boolean deleteEventByEventId(Event event) {
        eventRepository.delete(orikaMapperFacade.map(event, EventEntity.class));
        return true;
    }

    @Override
    public Optional<Event> updateEvent(Event event) {
        EventEntity eventEntity = orikaMapperFacade.map(event, EventEntity.class);
        return Optional.of(orikaMapperFacade.map(eventRepository.save(eventEntity), Event.class));
    }

    @Override
    public Optional<Event> findByEventId(String eventId) {
        return Optional.of(orikaMapperFacade.map(eventRepository.findByEventId(eventId).get(), Event.class));
    }

    @Override
    public PageGenerics<Event> getComingEventByTeamEventType(Team team, EventType eventType, int page, int size) {
        EventTypeEntity eventTypeEntity = orikaMapperFacade.map(eventType, EventTypeEntity.class);
        TeamEntity teamEntity = orikaMapperFacade.map(team, TeamEntity.class);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set( Calendar.HOUR, 23);
//        calendar.set( Calendar.MINUTE, 59 );
//        calendar.set( Calendar.SECOND, 59 );
        Date date = new Date();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
//        date = calendar.getTime();
        Date firstDate = new Date();
        firstDate.setHours(00);
        firstDate.setMinutes(00);
        firstDate.setSeconds(00);
        Set<EventEntity> todayEventsByTeamEventType = eventRepository.getTodayEventsByTeamEventType(
                teamEntity,
                eventTypeEntity,
                firstDate,
                date);
        Page<EventEntity> comingEventByTeamAndEventType = eventRepository.getComingEventByTeamEventType(
                teamEntity,
                eventTypeEntity,
                date,
                PageRequest.of(page, size));

        Set<Event> comingEvent = orikaMapperFacade.mapAsSet(
                comingEventByTeamAndEventType.getContent(),
                Event.class);
        Set<Event> todayEvents = orikaMapperFacade.mapAsSet(
                todayEventsByTeamEventType,
                Event.class);
        todayEvents.stream().forEach( eve -> comingEvent.add(eve));

        PageGenerics<Event> pageOfEvents = new PageGenerics<>();
        pageOfEvents.setData(comingEvent);
        int todayEventsSize = todayEvents.size();
        pageOfEvents.setSize(comingEventByTeamAndEventType.getNumberOfElements() + todayEventsSize);
        pageOfEvents.setActualPage(comingEventByTeamAndEventType.getNumber());
        pageOfEvents.setTotalPages(comingEventByTeamAndEventType.getTotalPages());
        pageOfEvents.setTotalData((int) comingEventByTeamAndEventType.getTotalElements() + todayEventsSize);
        return pageOfEvents;
    }

    @Override
    public Set<Event> findEventsByDateAndTeamAndEventType(Team team, EventType et, Date date1, Date date2) {
        return orikaMapperFacade.mapAsSet(
                eventRepository.findByDateAndTeamAndEventType(
                        orikaMapperFacade.map(team, TeamEntity.class),
                        orikaMapperFacade.map(et, EventTypeEntity.class),
                        date1,
                        date2)
                , Event.class);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsByAccountAndUser(Account account,
                                                                      User user,
                                                                      int page,
                                                                      int size,
                                                                      String sortBy) {
        Date currentDate = new Date();
        return getPreviousEventPageGenerics(account, user, page, size, sortBy, currentDate);
    }

    private PageGenerics<Event> getPreviousEventPageGenerics(Account account,
                                                     User user,
                                                     int page, int size, String sortBy, Date date) {
        Page<EventEntity> pageOfEventsByAccountAndInvitedUser =
                eventRepository.getPageOfEventsByAccountAndUser(
                        orikaMapperFacade.map(account, AccountEntity.class),
                        orikaMapperFacade.map(user, UserEntity.class),
                        date,
                        PageRequest.of(page, size, Sort.by(sortBy).descending()));

        PageGenerics<Event> pageOfEvents = new PageGenerics<>();
        pageOfEvents
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsByAccountAndInvitedUser.getContent(),
                                Event.class));
        pageOfEvents.setSize(pageOfEventsByAccountAndInvitedUser.getNumberOfElements());
        pageOfEvents.setActualPage(pageOfEventsByAccountAndInvitedUser.getNumber());
        pageOfEvents.setTotalPages(pageOfEventsByAccountAndInvitedUser.getTotalPages());
        pageOfEvents.setTotalData((int) pageOfEventsByAccountAndInvitedUser.getTotalElements());
        return pageOfEvents;
    }

    @Override
    public PageGenerics<Event> getPageOfPreviousEventsByAccountAndUserAndStartDate(
            Account account,
            User user,
            Date startDate,
            int page, int size, String sortBy) {
        return getPreviousEventPageGenerics(account, user, page, size, sortBy, startDate);
    }

    @Override
    public PageGenerics<Event> getPageOfToComeEventsByAccountAndUserAndStartDate(
            Account account,
            User user,
            Date startDate,
            int page, int size, String sortBy) {
        return getToComeEventPageGeneric(account, user, page, size, sortBy, startDate);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsByAccountAndUserAndTeam(Account account,
                                                                               User user,
                                                                               Team team,
                                                                               int page,
                                                                               int size,
                                                                               String sortBy) {
        Date currentDate = new Date();
        Page<EventEntity> pageOfEventsByAccountAndInvitedUserAndTeamId =
                eventRepository.getPageOfEventsByAccountAndUserAndTeam(
                        orikaMapperFacade.map(account, AccountEntity.class),
                        orikaMapperFacade.map(user, UserEntity.class),
                        orikaMapperFacade.map(team, TeamEntity.class),
                        currentDate,
                        PageRequest.of(page, size, Sort.by(sortBy).descending()));

        PageGenerics<Event> pageOfEvents = new PageGenerics<>();
        pageOfEvents
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsByAccountAndInvitedUserAndTeamId.getContent(),
                                Event.class));
        pageOfEvents.setSize(pageOfEventsByAccountAndInvitedUserAndTeamId.getNumberOfElements());
        pageOfEvents.setActualPage(pageOfEventsByAccountAndInvitedUserAndTeamId.getNumber());
        pageOfEvents.setTotalPages(pageOfEventsByAccountAndInvitedUserAndTeamId.getTotalPages());
        pageOfEvents.setTotalData((int) pageOfEventsByAccountAndInvitedUserAndTeamId.getTotalElements());
        return pageOfEvents;
    }

    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountAndUser(Account account,
                                                                      User user,
                                                                      int page,
                                                                      int size,
                                                                      String sortBy) {
        Date currentDate = new Date();
        return getToComeEventPageGeneric(account, user, page, size, sortBy, currentDate);
    }

    private PageGenerics<Event> getToComeEventPageGeneric(Account account, User user, int page, int size, String sortBy, Date currentDate) {
        Page<EventEntity> pageOfEventsToComeByAccountAndInvitedUser =
                eventRepository.getPageOfEventsToComeByAccountAndUser(
                        orikaMapperFacade.map(account, AccountEntity.class),
                        orikaMapperFacade.map(user, UserEntity.class),
                        currentDate,
                        PageRequest.of(page, size, Sort.by(sortBy)));

        PageGenerics<Event> pageOfEventsToCome = new PageGenerics<>();
        pageOfEventsToCome
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsToComeByAccountAndInvitedUser.getContent(),
                                Event.class));
        pageOfEventsToCome.setSize(pageOfEventsToComeByAccountAndInvitedUser.getNumberOfElements());
        pageOfEventsToCome.setActualPage(pageOfEventsToComeByAccountAndInvitedUser.getNumber());
        pageOfEventsToCome.setTotalPages(pageOfEventsToComeByAccountAndInvitedUser.getTotalPages());
        pageOfEventsToCome.setTotalData((int) pageOfEventsToComeByAccountAndInvitedUser.getTotalElements());
        return pageOfEventsToCome;
    }

    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccount(Account account,
                                                               int page,
                                                               int size,
                                                               String sortBy) {
        Date currentDate = new Date();
        Page<EventEntity> pageOfEventsToComeByAccountId =
                eventRepository.getPageOfEventsToComeByAccount(
                        orikaMapperFacade.map(account, AccountEntity.class),
                        currentDate,
                        PageRequest.of(page, size, Sort.by(sortBy).ascending()));

        PageGenerics<Event> pageOfEventsToCome = new PageGenerics<>();
        pageOfEventsToCome
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsToComeByAccountId.getContent(),
                                Event.class));
        pageOfEventsToCome.setSize(pageOfEventsToComeByAccountId.getNumberOfElements());
        pageOfEventsToCome.setActualPage(pageOfEventsToComeByAccountId.getNumber());
        pageOfEventsToCome.setTotalPages(pageOfEventsToComeByAccountId.getTotalPages());
        pageOfEventsToCome.setTotalData((int) pageOfEventsToComeByAccountId.getTotalElements());
        return pageOfEventsToCome;
    }


    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountAndTeam(Account account,
                                                                        Team team,
                                                                        int page,
                                                                        int size,
                                                                        String sortBy) {
        Date currentDate = new Date();
        Page<EventEntity> pageOfEventsToComeByAccountIdAndTeamId =
                eventRepository.getPageOfEventsToComeByAccountAndTeam(
                       orikaMapperFacade.map(account, AccountEntity.class),
                        orikaMapperFacade.map(team, TeamEntity.class),
                        currentDate,
                        PageRequest.of(page, size, Sort.by(sortBy)));

        PageGenerics<Event> pageOfEventsToCome = new PageGenerics<>();
        pageOfEventsToCome
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsToComeByAccountIdAndTeamId.getContent(),
                                Event.class));
        pageOfEventsToCome.setSize(pageOfEventsToComeByAccountIdAndTeamId.getNumberOfElements());
        pageOfEventsToCome.setActualPage(pageOfEventsToComeByAccountIdAndTeamId.getNumber());
        pageOfEventsToCome.setTotalPages(pageOfEventsToComeByAccountIdAndTeamId.getTotalPages());
        pageOfEventsToCome.setTotalData((int) pageOfEventsToComeByAccountIdAndTeamId.getTotalElements());
        return pageOfEventsToCome;
    }



    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountAndUserAndTeam(Account account,
                                                                              User user,
                                                                              Team team,
                                                                              int page,
                                                                              int size,
                                                                              String sortBy) {
        Date currentDate = new Date();
        Page<EventEntity> pageOfEventsToComeByAccountAndInvitedUserAndTeamId =
                eventRepository.getPageOfEventsToComeByAccountAndUserAndTeam(
                        orikaMapperFacade.map(account, AccountEntity.class),
                        orikaMapperFacade.map(user, UserEntity.class),
                        orikaMapperFacade.map(team, TeamEntity.class),
                        currentDate,
                        PageRequest.of(page, size, Sort.by(sortBy)));

        PageGenerics<Event> pageOfEventsToCome = new PageGenerics<>();
        pageOfEventsToCome
                .setData(
                        orikaMapperFacade.mapAsSet(
                                pageOfEventsToComeByAccountAndInvitedUserAndTeamId.getContent(),
                                Event.class));
        pageOfEventsToCome.setSize(pageOfEventsToComeByAccountAndInvitedUserAndTeamId.getNumberOfElements());
        pageOfEventsToCome.setActualPage(pageOfEventsToComeByAccountAndInvitedUserAndTeamId.getNumber());
        pageOfEventsToCome.setTotalPages(pageOfEventsToComeByAccountAndInvitedUserAndTeamId.getTotalPages());
        pageOfEventsToCome.setTotalData((int) pageOfEventsToComeByAccountAndInvitedUserAndTeamId.getTotalElements());
        return pageOfEventsToCome;
    }

    @Override
    public Set<Event> getEventsByDatesAndAccountAndTeam(Account account, Team team, Date startDate, Date endDate) {

        Set<EventEntity> events = eventRepository.getEventsByDatesAndAccountAndTeam(
                orikaMapperFacade.map(account, AccountEntity.class),
                orikaMapperFacade.map(team, TeamEntity.class),
                startDate,
                endDate);

        return orikaMapperFacade.mapAsSet(events, Event.class);
    }
}
