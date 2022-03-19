package com.zak.domain.spi;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.*;
import java.util.Optional;
import java.util.Set;

public interface RetroSprintPersistencePort {

    Optional<RetroSprint> saveRetroSprint(RetroSprint retroSprint);
    Optional<RetroSprint> findByRetroSprintId(String retroSprintId);
    Set<RetroSprint> findByTeam(Team team);
    Set<RetroSprint> findByTeamAndStatus(Team team, EventStatus status);
    boolean deleteRetroSprintByRetroSprintId(RetroSprint retroSprint);
}
