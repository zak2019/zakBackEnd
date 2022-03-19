package com.zak.application.service.api;

import com.zak.domain.model.Team;

import java.util.Set;

public interface TeamService {

    Set<Team> getTeamsByAccountId(String accountId);
    Team getTeamByTeamId(String teamId);
    Team createTeam(String accountId, Team team);
    Team updateTeam(Team team);
    Team addUserToTeam(String userId, String teamId);
    Team linkUsersToTeam(Team team);
    Team addUserToNewTeam(String accountId, String userId, Team team);
}
