package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.spi.RetroSprintCommentGroupPersistencePort;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import com.zak.persistence.jpa.repository.RetroSprintCommentGroupRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RetroSprintCommentGroupAdapter implements RetroSprintCommentGroupPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintCommentGroupRepository retroSprintCommentGroupRepository;

    @Autowired
    public RetroSprintCommentGroupAdapter(MapperFacade orikaMapperFacade,
                                          RetroSprintCommentGroupRepository retroSprintCommentGroupRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.retroSprintCommentGroupRepository = retroSprintCommentGroupRepository;
    }


    @Override
    public Optional<RetroSprintCommentGroup> createRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        RetroSprintCommentGroupEntity groupEntity =
                orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentGroupRepository.save(groupEntity), RetroSprintCommentGroup.class));
    }

    @Override
    public Optional<RetroSprintCommentGroup> updateRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        RetroSprintCommentGroupEntity groupEntity = orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentGroupRepository.save(groupEntity), RetroSprintCommentGroup.class));
    }

    @Override
    public boolean deleteRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        RetroSprintCommentGroupEntity groupEntity = orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);
        retroSprintCommentGroupRepository.delete(groupEntity);
        return true;
    }

    @Override
    public Optional<RetroSprintCommentGroup> findByRetroSprintCommentGroupId(String retroSprintCommentGroupId) {
        return Optional.of(orikaMapperFacade.map(
                retroSprintCommentGroupRepository.findByRetroSprintCommentGroupId(retroSprintCommentGroupId).get(),
                RetroSprintCommentGroup.class));
    }
}
