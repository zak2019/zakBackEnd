package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.Account;
import com.zak.domain.model.Team;
import com.zak.domain.spi.TeamPersistencePort;
import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.TeamEntity;
import com.zak.persistence.jpa.repository.TeamRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class TeamAdapter implements TeamPersistencePort {

    private final TeamRepository teamRepository;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public TeamAdapter(TeamRepository teamRepository,
                       MapperFacade orikaMapperFacade) {
        this.teamRepository = teamRepository;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @Override
    public Set<Team> getTeamsByAccount(Account account) {
        AccountEntity accountEntity = orikaMapperFacade.map(account, AccountEntity.class);
        return orikaMapperFacade.mapAsSet(teamRepository.findByAccount(accountEntity), Team.class);
    }

    @Override
    public Team createTeam(Team team) {
        TeamEntity entity = orikaMapperFacade.map(team, TeamEntity.class);
        return orikaMapperFacade.map(teamRepository.save(entity), Team.class);
    }

    @Override
    public Team updateTeam(Team team) {
        return createTeam(team);
    }

    @Override
    public Team findTeamByTeamId(String teamId) {
        return orikaMapperFacade.map(teamRepository.findByTeamId(teamId), Team.class);
    }

}
