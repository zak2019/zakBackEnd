package com.zak.persistence.jpa.repository;

import com.zak.domain.enums.EventStatus;
import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintRepository extends JpaRepository<RetroSprintEntity, Long> {

    Optional<RetroSprintEntity> findByRetroSprintId(String retroSprintId);

    @Query("select r" +
            " from RetroSprintEntity r" +
            " where r.eventRetro is not null" +
            " and r.eventRetro.team is not null" +
            " and r.eventRetro.team = :team" +
            " order by r.creationDate desc")
    Set<RetroSprintEntity> findByTeam( @Param("team") TeamEntity team);

    @Query("select r" +
            " from RetroSprintEntity r" +
            " where r.eventRetro is not null" +
            " and r.eventRetro.team is not null" +
            " and r.eventRetro.team = :team" +
            " and r.status = :status" +
            " order by r.creationDate desc")
    Set<RetroSprintEntity> findByTeamAndStatus(@Param("team") TeamEntity team,
                                               @Param("status") EventStatus status);

}
