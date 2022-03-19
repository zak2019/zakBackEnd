package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.exception.*;
import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.spi.*;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class EventServiceAdapter implements EventService {

    private final EventPersistencePort eventPersistencePort;
    private final TeamPersistencePort teamPersistencePort;
    private final AccountService accountService;
    private final UserService userService;
    private final EmailService emailService;
    private final EventTypeService eventTypeService;
    private final NotificationService notificationService;


    @Autowired
    private IdGererator idGererator;

    public EventServiceAdapter(EventPersistencePort eventPersistencePort,
                               TeamPersistencePort teamPersistencePort,
                               AccountService accountService,
                               UserService userService,
                               EmailService emailService,
                               EventTypeService eventTypeService,
                               NotificationService notificationService) {
        this.eventPersistencePort = eventPersistencePort;
        this.teamPersistencePort = teamPersistencePort;
        this.accountService = accountService;
        this.userService = userService;
        this.emailService = emailService;
        this.eventTypeService = eventTypeService;
        this.notificationService = notificationService;
    }

    @Override
    public Event getEventByEventId(String eventId) {
        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        if (!event.isPresent()) {
            throw new EventException("Event not found");
        }
        return event.get();
    }

    @Override
    public PageGenerics<Event> getComingEventByTeamIdAndEventType(String teamId, String eventType, int page, int size) {
        EventType et = getAndCheckExistsEventType(eventType);
        Team team = getAndCheckTeam(teamId);
        return eventPersistencePort.getComingEventByTeamEventType(team, et, page, size);
    }

    private EventType getAndCheckExistsEventType(String eventType) {
        Optional<EventType> et = eventTypeService.getEventTypeByEventTypeCode(eventType);
        if (!et.isPresent()) {
            throw new EventTypeException();
        }
        return et.get();
    }

    @Override
    public Set<Event> getTodayEventsByTeamIdAndEventType(String teamId, String eventType) {
        EventType et = getAndCheckExistsEventType(eventType);
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setHours(00);
        date1.setMinutes(00);
        date1.setSeconds(00);

        date2.setHours(23);
        date2.setMinutes(59);
        date2.setSeconds(59);

        Team team = getAndCheckTeam(teamId);

        Set<Event> events = eventPersistencePort.findEventsByDateAndTeamAndEventType(team, et, date1, date2);

        return events;
    }

    @Override
    public Set<Event> createMultipleEvent(String accountId, Event event, Set<String> dateList,
                                          String eventHour, String eventEndHour) {
        Set<Event> savedEvents = new HashSet<Event>();
        LocalTime localTime = LocalTime.parse(eventHour);
        LocalTime localEndTime = LocalTime.parse(eventEndHour);
        dateList.forEach(eventDate -> {
            LocalDateTime date = LocalDate.parse(eventDate)
                    .atTime(localTime.getHour(), localTime.getMinute(), 00);
            LocalDateTime endDate = LocalDate.parse(eventDate)
                    .atTime(localEndTime.getHour(), localEndTime.getMinute(), 00);

            event.setEventDate(java.sql.Timestamp.valueOf(date));
            event.setEventEndDate(java.sql.Timestamp.valueOf(endDate));
            savedEvents.add(createEvent(accountId, event).get());
        });
        return savedEvents;
    }

    @Override
    public Optional<Event> createEvent(String accountId, Event event) {
        String connectedUserId = userService.getConnectedUserId();
        Set<User> users = new HashSet<User>();
        event.getUsers().forEach(user -> {
            users.add(userService.findUserByUserId(user.getUserId()).get());
        });

        if (event.getTeam() != null) {
            event.setTeam(teamPersistencePort.findTeamByTeamId(event.getTeam().getTeamId()));
        }

        EventType eventType = event.getEventType();
        if (eventType == null) {
            throw new EventTypeException("Event type is mandatory");
        }

        Optional<EventType> eventTypeByEventTypeId = eventTypeService.getEventTypeByEventTypeId(eventType.getEventTypeId());
        if (!eventTypeByEventTypeId.isPresent()) {
            throw new EventTypeException();
        }

        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Date eventDate = event.getEventDate();
        eventDate.setSeconds(00);
        event.setEventDate(eventDate);
        event.setUsers(users);
        event.setCreatedBy(userService.findUserByUserId(connectedUserId).get());
        event.setAccount(account);
        event.setCreationDate(new Date());
        event.setEventId(idGererator.generateUniqueId());
        event.setEventType(eventTypeByEventTypeId.get());
        Optional<Event> savedEvent = eventPersistencePort.createEvent(event);
        if (savedEvent.isPresent()) {
            notifyUsers(users, savedEvent.get(), NotificationTypeEnum.AFFECTED_TO_EVENT);
            notifyUsersByMail(users, event);
        }
        return savedEvent;
    }

    @Override
    public boolean deleteEventByEventId(String eventId) {
        Event existingEvent = getEvent(eventId);

        if (existingEvent.getEventDate().compareTo(new Date()) <= 0) {
            throw new EventException(String.format("You can't delete the event %s", existingEvent.getName()));
        }

        boolean deleteResponse = this.eventPersistencePort.deleteEventByEventId(existingEvent);
        if (deleteResponse) {
            notifyUsersByMailWhenEventCancelled(existingEvent);
        }
        return deleteResponse;
    }

    private Event getEvent(String eventId) {
        Optional<Event> existingEvent = this.eventPersistencePort.findByEventId(eventId);
        if (!existingEvent.isPresent()) {
            throw new EventException();
        }
        return existingEvent.get();
    }

    private void notifyUsersByMailWhenEventCancelled(Event event) {

        event.getUsers().forEach(user -> {
            emailService.sendEmail(
                    user.getEmail(),
                    "Event canceled",
                    "<h3>" + event.getAccount().getAccountName() + " project</h1>" +
                            "<p>An event that you are affected to has been canceled: <br>" +
                            "Event name: " + event.getName() + "<br>" +
                            "Event Project: " + event.getAccount().getAccountName() + "<br>" +
                            (event.getTeam() != null ? "Event Team: " + event.getTeam().getTeamName() + "<br>" : "") +
                            "Event date: " + event.getEventDate() + "<br>" +
                            "</p>"
            );
        });
    }

    private void notifyUsersByMail(Set<User> users, Event event) {

        users.forEach(user -> {
            emailService.sendEmail(
                    user.getEmail(),
                    "You have been affected to an event",
                    "<h3>" + event.getAccount().getAccountName() + " project</h1>" +
                            "<p>You have been affected to this event: <br>" +
                            "Event name: " + event.getName() + "<br>" +
                            "Event Project: " + event.getAccount().getAccountName() + "<br>" +
                            (event.getTeam() != null ? "Event Team: " + event.getTeam().getTeamName() + "<br>" : "") +
                            "Event date: " + event.getEventDate() + "<br>" +
                            "</p>"
            );
        });
    }

    private void notifyUsers(Set<User> users, Event event, NotificationTypeEnum type) {
        users.forEach(user -> {
            Notification notification = new Notification();
            notification.setUserToNotify(user);
            notification.setEvent(event);
            notificationService.sendNotificationToUser(notification, type);
        });
    }

    @Override
    public Optional<Event> updateEvent(Event event) {
        Event existingEvent = getEvent(event.getEventId());
        event.setId(existingEvent.getId());
        return eventPersistencePort.updateEvent(event);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsByAccountByInvitedUser(String accountId,
                                                                     String userId,
                                                                     int page,
                                                                     int size,
                                                                     String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return eventPersistencePort.getPageOfEventsByAccountAndUser(account, user.get(), page, size, sortBy);
    }

    @Override
    public PageGenerics<Event> getPageOfPreviousEventsByAccountByInvitedUserAndStartingFrom(
            String accountId,
            String userId,
            String eventId,
            int page, int size, String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        return eventPersistencePort.getPageOfPreviousEventsByAccountAndUserAndStartDate(
                account,
                user.get(), event.get().getEventDate(),
                page, size, sortBy);
    }

    @Override
    public PageGenerics<Event> getPageOfToComeEventsByAccountByInvitedUserAndStartingFrom(
            String accountId,
            String userId,
            String eventId,
            int page, int size, String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        return eventPersistencePort.getPageOfToComeEventsByAccountAndUserAndStartDate(
                account,
                user.get(), event.get().getEventDate(),
                page, size, sortBy);
    }


    @Override
    public PageGenerics<Event> getPageOfEventsByAccountByInvitedUserAndTeamId(String accountId,
                                                                              String userId,
                                                                              String teamId,
                                                                              int page,
                                                                              int size,
                                                                              String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Team team = getAndCheckTeam(teamId);

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return eventPersistencePort.getPageOfEventsByAccountAndUserAndTeam(account, user.get(), team, page, size, sortBy);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountByInvitedUser(String accountId,
                                                                           String userId,
                                                                           int page,
                                                                           int size,
                                                                           String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return eventPersistencePort.getPageOfEventsToComeByAccountAndUser(account, user.get(), page, size, sortBy);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountId(String accountId,
                                                                int page,
                                                                int size,
                                                                String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        return eventPersistencePort.getPageOfEventsToComeByAccount(account, page, size, sortBy);
    }


    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountIdAndTeamId(String accountId,
                                                                         String teamId,
                                                                         int page,
                                                                         int size,
                                                                         String sortBy) {
        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Team team = getAndCheckTeam(teamId);

        return eventPersistencePort.getPageOfEventsToComeByAccountAndTeam(account, team, page, size, sortBy);
    }

    @Override
    public PageGenerics<Event> getPageOfEventsToComeByAccountByInvitedUserAndTeamId(String accountId,
                                                                                    String userId,
                                                                                    String teamId,
                                                                                    int page,
                                                                                    int size,
                                                                                    String sortBy) {

        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException();
        }

        Team team = getAndCheckTeam(teamId);

        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        return eventPersistencePort
                .getPageOfEventsToComeByAccountAndUserAndTeam(account, user.get(), team, page, size, sortBy);
    }

    private Team getAndCheckTeam(String teamId) {
        Team team = teamPersistencePort.findTeamByTeamId(teamId);
        if (team == null) {
            throw new TeamException("Team not found");
        }
        return team;
    }

    @Override
    public Set<Event> getEventsByDatesAndAccountIdAndTeamId(String accountId,
                                                            String teamId,
                                                            Date startDate,
                                                            Date endDate) {

        Account account = accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException("Account not found");
        }

        Team team = getAndCheckTeam(teamId);

        return eventPersistencePort
                .getEventsByDatesAndAccountAndTeam(account, team, startDate, endDate);
    }
}
