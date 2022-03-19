package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.RetroSprintCommentGroupVote;
import com.zak.domain.model.RetroSprintCommentVote;
import com.zak.domain.model.User;
import com.zak.domain.spi.RetroSprintCommentGroupVotePersistencePort;
import com.zak.domain.spi.RetroSprintCommentVotePersistencePort;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupVoteEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentVoteEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.repository.RetroSprintCommentGroupVoteRepository;
import com.zak.persistence.jpa.repository.RetroSprintCommentVoteRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RetroSprintCommentVoteAdapter implements RetroSprintCommentVotePersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintCommentVoteRepository retroSprintCommentVoteRepository;

    @Autowired
    public RetroSprintCommentVoteAdapter(MapperFacade orikaMapperFacade,
                                         RetroSprintCommentVoteRepository retroSprintCommentVoteRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.retroSprintCommentVoteRepository = retroSprintCommentVoteRepository;
    }


    @Override
    public Optional<RetroSprintCommentVote> createRetroSprintCommentVote(RetroSprintCommentVote vote) {
        RetroSprintCommentVoteEntity voteEntity =
                orikaMapperFacade.map(vote, RetroSprintCommentVoteEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentVoteRepository.save(voteEntity), RetroSprintCommentVote.class));
    }

    @Override
    public boolean deleteVoteByRetroSprintCommentVoteId(String voteId) {
        retroSprintCommentVoteRepository.deleteVoteByRetroSprintCommentVoteId(voteId);
        return true;
    }

    @Override
    public Optional<RetroSprintCommentVote> findVoteByRetroSprintCommentVoteId(String voteId) {
        Optional<RetroSprintCommentVoteEntity> v =
                retroSprintCommentVoteRepository.findByRetroSprintCommentVoteId(voteId);
        return v.isPresent() ? Optional.of(orikaMapperFacade.map(v.get(), RetroSprintCommentVote.class)) : Optional.empty();
    }
}
