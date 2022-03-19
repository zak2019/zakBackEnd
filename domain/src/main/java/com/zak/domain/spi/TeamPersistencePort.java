package com.zak.domain.spi;


import com.zak.domain.model.Account;
import com.zak.domain.model.Team;

import java.util.Set;

public interface TeamPersistencePort {

    Set<Team> getTeamsByAccount(Account account);
    Team createTeam(Team team);
    Team updateTeam(Team team);
    Team findTeamByTeamId(String TeamId);
}
