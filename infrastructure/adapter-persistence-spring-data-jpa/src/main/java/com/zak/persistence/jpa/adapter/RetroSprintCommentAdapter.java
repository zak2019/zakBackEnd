package com.zak.persistence.jpa.adapter;

import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintComment;
import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.spi.RetroSprintCommentPersistencePort;
import com.zak.persistence.jpa.entity.RetroSprintCommentEntity;
import com.zak.persistence.jpa.entity.RetroSprintCommentGroupEntity;
import com.zak.persistence.jpa.entity.RetroSprintEntity;
import com.zak.persistence.jpa.repository.RetroSprintCommentRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class RetroSprintCommentAdapter implements RetroSprintCommentPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final RetroSprintCommentRepository retroSprintCommentRepository;

    @Autowired
    public RetroSprintCommentAdapter(MapperFacade orikaMapperFacade,
                                     RetroSprintCommentRepository retroSprintCommentRepository) {
        this.orikaMapperFacade = orikaMapperFacade;
        this.retroSprintCommentRepository = retroSprintCommentRepository;
    }


    @Override
    public Optional<RetroSprintComment> createRetroSprintComment(RetroSprintComment retroSprintComment) {
        RetroSprintCommentEntity commentEntity = orikaMapperFacade.map(retroSprintComment, RetroSprintCommentEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentRepository.save(commentEntity), RetroSprintComment.class));
    }

    @Override
    public Optional<RetroSprintComment> updateRetroSprintComment(RetroSprintComment retroSprintComment) {
        RetroSprintCommentEntity commentEntity = orikaMapperFacade.map(retroSprintComment, RetroSprintCommentEntity.class);
        return Optional.of(orikaMapperFacade.map(retroSprintCommentRepository.save(commentEntity), RetroSprintComment.class));
    }

    // to do delete children with comment
    @Override
    public boolean deleteRetroSprintComment(RetroSprintComment retroSprintComment) {
        RetroSprintCommentEntity commentEntity = orikaMapperFacade.map(retroSprintComment, RetroSprintCommentEntity.class);
        retroSprintCommentRepository.delete(commentEntity);
        return true;
    }

    @Override
    public Optional<RetroSprintComment> findByRetroSprintCommentId(String retroSprintCommentId) {
        return Optional.of(orikaMapperFacade.map(
                retroSprintCommentRepository.findByRetroSprintCommentId(retroSprintCommentId).get(),
                RetroSprintComment.class));
    }

    @Override
    public Set<RetroSprintComment> findByRetroSprintCommentGroup(RetroSprintCommentGroup retroSprintCommentGroup) {
        RetroSprintCommentGroupEntity retroSprintCommentGroupEntity =
                orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);
        return orikaMapperFacade.mapAsSet(
                retroSprintCommentRepository.findByRetroSprintCommentGroup(retroSprintCommentGroupEntity),
                RetroSprintComment.class);
    }

    @Override
    public Set<RetroSprintComment> findBySprintRetroCommentGroupAndByCommentTypeAndByPositions(
            RetroSprintCommentGroup retroSprintCommentGroup,
            RetroSprintCommentType type,
            int firstPosition,
            int secondPosition) {
        RetroSprintCommentGroupEntity groupEntity = orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);

        return orikaMapperFacade
                .mapAsSet(retroSprintCommentRepository
                        .findBySprintRetroCommentGroupAndByCommentTypeAndByPositions(
                                groupEntity,
                                type,
                                firstPosition,
                                secondPosition), RetroSprintComment.class);
    }

    @Override
    public Set<RetroSprintComment> findBySprintRetroGroupAndByCommentTypeStartingFromPosition(
            RetroSprintCommentGroup retroSprintCommentGroup,
            RetroSprintCommentType type,
            int position) {
        RetroSprintCommentGroupEntity groupEntity = orikaMapperFacade.map(retroSprintCommentGroup, RetroSprintCommentGroupEntity.class);

        return orikaMapperFacade
                .mapAsSet(retroSprintCommentRepository
                        .findBySprintRetroGroupAndByCommentTypeStartingFromPosition(
                                groupEntity,
                                type,
                                position), RetroSprintComment.class);
    }
}
