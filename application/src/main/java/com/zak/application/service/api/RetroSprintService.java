package com.zak.application.service.api;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.RetroSprint;

import java.util.Optional;
import java.util.Set;

public interface RetroSprintService {
    Optional<RetroSprint> createRetroSprint(String retroEventId);
    Optional<RetroSprint> updateRetroSprintStatus(String retroSprintId,  EventStatus status);
    Optional<RetroSprint> startRetroSprintBoard(RetroSprint retroSprint);
    Optional<RetroSprint> updateRetroSprint(RetroSprint retroSprint);
    Optional<RetroSprint> updateRetroSprintWeatherStatus(String retroSprintId,  EventStatus status);
    Optional<RetroSprint> updateRetroSprintBoardStatus(String retroSprintId,  EventStatus status);
    RetroSprint getRetroSprintByRetroSprintId(String retroSprintId);
    Set<RetroSprint> getRetroSprintsByTeamId(String teamId);
    Set<RetroSprint> getRetroSprintsByTeamIdAnsStatus(String teamId, EventStatus status);
    boolean deleteRetroSprintByRetroSprintId(String retroSprintId);

}
