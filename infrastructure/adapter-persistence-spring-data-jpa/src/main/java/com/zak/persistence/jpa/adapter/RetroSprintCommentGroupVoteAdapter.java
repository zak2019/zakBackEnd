package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.RetroSprintCommentGroupVote;
import com.zak.domain.model.User;
import com.zak.domain.spi.RetroSprintCommentGroupVotePersistencePort;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupVoteEntity;
import com.zak.persistence.jpa.entity.UserEntity;
import com.zak.persistence.jpa.repository.RetroSprintCommentGroupVoteRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RetroSprintCommentGroupVoteAdapter implements RetroSprintCommentGroupVotePersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintCommentGroupVoteRepository retroSprintCommentGroupVoteRepository;

    @Autowired
    public RetroSprintCommentGroupVoteAdapter(MapperFacade orikaMapperFacade,
                                              RetroSprintCommentGroupVoteRepository retroSprintCommentGroupVoteRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.retroSprintCommentGroupVoteRepository = retroSprintCommentGroupVoteRepository;
    }


    @Override
    public Optional<RetroSprintCommentGroupVote> createRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote) {
        RetroSprintCommentGroupVoteEntity voteEntity =
                orikaMapperFacade.map(vote, RetroSprintCommentGroupVoteEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentGroupVoteRepository.save(voteEntity), RetroSprintCommentGroupVote.class));
    }

    @Override
    public Optional<RetroSprintCommentGroupVote> updateRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote) {
        RetroSprintCommentGroupVoteEntity voteEntity = orikaMapperFacade.map(vote, RetroSprintCommentGroupVoteEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentGroupVoteRepository.save(voteEntity), RetroSprintCommentGroupVote.class));
    }

    @Override
    public boolean deleteRetroSprintCommentGroupVote(String voteId) {
        retroSprintCommentGroupVoteRepository.deleteVoteByRetroSprintCommentGroupVoteId(voteId);
        return true;
    }

    @Override
    public Optional<RetroSprintCommentGroupVote> findByRetroSprintCommentGroupAndUser(RetroSprintCommentGroup group, User user) {
        RetroSprintCommentGroupEntity groupEntity = orikaMapperFacade.map(group, RetroSprintCommentGroupEntity.class);
        UserEntity userEntity = orikaMapperFacade.map(user, UserEntity.class);
        Optional<RetroSprintCommentGroupVoteEntity> v =
                retroSprintCommentGroupVoteRepository.findByRetroSprintCommentGroupAndUser(groupEntity, userEntity);
        return v.isPresent() ? Optional.of(orikaMapperFacade.map(v.get(), RetroSprintCommentGroupVote.class)) : Optional.empty();
    }
}
