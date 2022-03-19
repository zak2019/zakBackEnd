package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.AccountEntity;
import com.zak.persistence.jpa.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    TeamEntity findByTeamId(String teamId);
    Set<TeamEntity> findByAccount(AccountEntity account);
}
