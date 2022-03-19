package com.zak.domain.spi;

import com.zak.domain.model.*;
import com.zak.domain.model.util.PageGenerics;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface EventPersistencePort {

    Optional<Event> createEvent(Event event);
    Optional<Event> updateEvent(Event event);
    boolean deleteEventByEventId(Event event);
    Optional<Event> findByEventId(String eventId);
    PageGenerics<Event> getComingEventByTeamEventType(Team team, EventType eventType, int page, int size);
    Set<Event> findEventsByDateAndTeamAndEventType(Team team, EventType et, Date date1, Date date2);
    PageGenerics<Event> getPageOfEventsByAccountAndUser(Account account,
                                                               User user,
                                                               int page,
                                                               int size,
                                                               String sortBy);
    PageGenerics<Event> getPageOfPreviousEventsByAccountAndUserAndStartDate(Account account,
                                                               User user,
                                                               Date startDate,
                                                               int page,
                                                               int size,
                                                               String sortBy);
    PageGenerics<Event> getPageOfToComeEventsByAccountAndUserAndStartDate(Account account,
                                                               User user,
                                                               Date startDate,
                                                               int page,
                                                               int size,
                                                               String sortBy);

    PageGenerics<Event> getPageOfEventsByAccountAndUserAndTeam(Account account,
                                                               User user,
                                                               Team team,
                                                               int page,
                                                               int size,
                                                               String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccount(Account account,
                                                         int page,
                                                         int size,
                                                         String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountAndTeam(Account account,
                                                                  Team team,
                                                                  int page,
                                                                  int size,
                                                                  String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountAndUser(Account account,
                                                               User user,
                                                               int page,
                                                               int size,
                                                               String sortBy);

    PageGenerics<Event> getPageOfEventsToComeByAccountAndUserAndTeam(Account account,
                                                               User user,
                                                               Team team,
                                                               int page,
                                                               int size,
                                                               String sortBy);
    Set<Event> getEventsByDatesAndAccountAndTeam(Account account,
                                                 Team team,
                                                 Date startDate,
                                                 Date endDate);
}
