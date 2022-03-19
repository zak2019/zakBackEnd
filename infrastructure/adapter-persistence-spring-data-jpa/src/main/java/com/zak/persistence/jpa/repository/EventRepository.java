package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("select e" +
            " from EventEntity e" +
            " where e.eventType = :type" +
            " and e.team = :team" +
            " and e.eventDate > :date" +
            " order by  e.eventDate asc ")
    Page<EventEntity> getComingEventByTeamEventType(@Param("team") TeamEntity team,
                                                    @Param("type") EventTypeEntity type,
                                                    @Param("date") Date date,
                                                    Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where e.eventType = :type" +
            " and e.team = :team" +
            " and e.eventDate >= :date1" +
            " and e.eventDate <= :date2" +
            " order by  e.eventDate asc ")
    Set<EventEntity> getTodayEventsByTeamEventType(@Param("team") TeamEntity team,
                                                    @Param("type") EventTypeEntity type,
                                                    @Param("date1") Date date1,
                                                    @Param("date2") Date date2);

    Optional<EventEntity> findByEventId(String eventId);

    @Query("select e" +
            " from EventEntity e" +
            " where e.team is not null" +
            " and e.team = :team" +
            " and e.eventType = :eventType" +
            " and e.eventDate >= :date1" +
            " and e.eventDate <= :date2" +
            " order by e.eventDate desc")
    Set<EventEntity> findByDateAndTeamAndEventType( @Param("team") TeamEntity team,
                                                    @Param("eventType") EventTypeEntity eventType,
                                                    @Param("date1") Date date1,
                                                    @Param("date2") Date date2);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and :user IN elements(e.users)" +
            " and e.team is not null" +
            " and e.team = :team" +
            " and e.eventDate < :currentDate" +
            " order by e.eventDate desc")
    Page<EventEntity> getPageOfEventsByAccountAndUserAndTeam(
            @Param("account") AccountEntity account,
            @Param("user") UserEntity user,
            @Param("team") TeamEntity team,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and :user IN elements(e.users)" +
            " and e.eventDate <=:currentDate" +
            " order by e.eventDate desc")
    Page<EventEntity> getPageOfEventsByAccountAndUser(
            @Param("account") AccountEntity account,
            @Param("user") UserEntity user,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :user IN elements(e.users)" +
            " and :account = e.account" +
            " and e.eventDate >= :currentDate")
    Page<EventEntity> getPageOfEventsToComeByAccountAndUser(
            @Param("account") AccountEntity account,
            @Param("user") UserEntity user,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and e.eventDate >= :currentDate")
    Page<EventEntity> getPageOfEventsToComeByAccount(
            @Param("account") AccountEntity account,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and e.team is not null" +
            " and e.team = :team" +
            " and e.eventDate > :currentDate")
    Page<EventEntity> getPageOfEventsToComeByAccountAndTeam(
            @Param("account") AccountEntity account,
            @Param("team") TeamEntity team,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and :user IN elements(e.users)" +
            " and e.team is not null" +
            " and e.team = :team" +
            " and e.eventDate > :currentDate")
    Page<EventEntity> getPageOfEventsToComeByAccountAndUserAndTeam(
            @Param("account") AccountEntity account,
            @Param("user") UserEntity user,
            @Param("team") TeamEntity team,
            @Param("currentDate") Date currentDate,
            Pageable pageable);

    @Query("select e" +
            " from EventEntity e" +
            " where :account = e.account" +
            " and e.team is not null" +
            " and e.team = :team" +
            " and e.eventDate >= :startDate" +
            " and e.eventDate <= :endDate")
    Set<EventEntity> getEventsByDatesAndAccountAndTeam(@Param("account") AccountEntity account,
                                                       @Param("team") TeamEntity team,
                                                       @Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate);
}
