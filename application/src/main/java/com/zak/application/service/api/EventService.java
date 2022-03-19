package com.zak.application.service.api;

import com.zak.domain.model.Event;
import com.zak.domain.model.util.PageGenerics;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface EventService {
    public Event getEventByEventId(String eventId);
    PageGenerics<Event>  getComingEventByTeamIdAndEventType(String teamId, String eventType, int page, int size);
    public Set<Event> getTodayEventsByTeamIdAndEventType(String teamId, String eventType);
    Set<Event> createMultipleEvent(String accountId, Event event, Set<String> dateList, String eventHour, String eventEndHour);
    Optional<Event> createEvent(String accountId, Event event);
    boolean deleteEventByEventId(String eventId);
    Optional<Event> updateEvent(Event event);
    PageGenerics<Event> getPageOfEventsByAccountByInvitedUser(String accountId,
                                                              String userId,
                                                              int page,
                                                              int size,
                                                              String sortBy);
    PageGenerics<Event> getPageOfPreviousEventsByAccountByInvitedUserAndStartingFrom(String accountId,
                                                              String userId,
                                                              String eventId,
                                                              int page,
                                                              int size,
                                                              String sortBy);

    PageGenerics<Event> getPageOfToComeEventsByAccountByInvitedUserAndStartingFrom(String accountId,
                                                              String userId,
                                                              String eventId,
                                                              int page,
                                                              int size,
                                                              String sortBy);

    PageGenerics<Event> getPageOfEventsByAccountByInvitedUserAndTeamId(String accountId,
                                                              String userId,
                                                              String teamId,
                                                              int page,
                                                              int size,
                                                              String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountId(String accountId,
                                                       int page,
                                                       int size,
                                                       String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountIdAndTeamId(String accountId,
                                                             String teamId,
                                                             int page,
                                                             int size,
                                                             String sortBy);


    PageGenerics<Event> getPageOfEventsToComeByAccountByInvitedUser(String accountId,
                                                              String userId,
                                                              int page,
                                                              int size,
                                                              String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountByInvitedUserAndTeamId(String accountId,
                                                              String userId,
                                                              String teamId,
                                                              int page,
                                                              int size,
                                                              String sortBy);

    Set<Event> getEventsByDatesAndAccountIdAndTeamId(String accountId,
                                                     String teamId,
                                                     Date startDate,
                                                     Date endDate);

}
