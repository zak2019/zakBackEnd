package com.zak.persistence.jpa.adapter;

import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.*;
import com.zak.domain.spi.RetroSprintPersistencePort;
import com.zak.persistence.jpa.entity.*;
import com.zak.persistence.jpa.repository.RetroSprintRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class RetroSprintAdapter implements RetroSprintPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintRepository retroSprintRepository;

    @Autowired
    public RetroSprintAdapter(MapperFacade orikaMapperFacade,
                              RetroSprintRepository retroSprintRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.retroSprintRepository = retroSprintRepository;
    }

    @Override
    public Optional<RetroSprint> saveRetroSprint(RetroSprint retroSprint) {
        RetroSprintEntity retroSprintEntity = orikaMapperFacade.map(retroSprint, RetroSprintEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintRepository.save(retroSprintEntity), RetroSprint.class));
    }

    @Override
    public Optional<RetroSprint> findByRetroSprintId(String retroSprintId) {
        return Optional.of(orikaMapperFacade.map(retroSprintRepository.findByRetroSprintId(retroSprintId).get(), RetroSprint.class));
    }


    @Override
    public Set<RetroSprint> findByTeam(Team team) {
        TeamEntity teamEntity = orikaMapperFacade.map(team, TeamEntity.class);
        return orikaMapperFacade.mapAsSet(retroSprintRepository.findByTeam(teamEntity), RetroSprint.class);
    }

    @Override
    public Set<RetroSprint> findByTeamAndStatus(Team team, EventStatus status) {
        TeamEntity teamEntity = orikaMapperFacade.map(team, TeamEntity.class);
        return orikaMapperFacade.mapAsSet(retroSprintRepository.findByTeamAndStatus(teamEntity, status), RetroSprint.class);
    }

    @Override
    public boolean deleteRetroSprintByRetroSprintId(RetroSprint retroSprint) {
        retroSprintRepository.delete(orikaMapperFacade.map(retroSprint, RetroSprintEntity.class));
        return true;
    }
}
