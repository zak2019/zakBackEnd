package com.zak.persistence.jpa.adapter;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventComment;
import com.zak.domain.spi.EventCommentPersistencePort;
import com.zak.persistence.jpa.entity.EventCommentEntity;
import com.zak.persistence.jpa.entity.EventEntity;
import com.zak.persistence.jpa.repository.EventCommentRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class EventCommentAdapter implements EventCommentPersistencePort {

    private final MapperFacade orikaMapperFacade;
    private final EventCommentRepository eventCommentRepository;

    @Autowired
    public EventCommentAdapter(MapperFacade orikaMapperFacade,
                               EventCommentRepository eventCommentRepository){
    this.orikaMapperFacade = orikaMapperFacade;
    this.eventCommentRepository = eventCommentRepository;
    }


    @Override
    public Optional<EventComment> createEventComment(EventComment eventComment) {
        EventCommentEntity commentEntity = orikaMapperFacade.map(eventComment, EventCommentEntity.class);
        return Optional.of(orikaMapperFacade.map(eventCommentRepository.save(commentEntity), EventComment.class));
    }

    @Override
    public Optional<EventComment> updateEventComment(EventComment eventComment) {
        EventCommentEntity commentEntity = orikaMapperFacade.map(eventComment, EventCommentEntity.class);
        return Optional.of(orikaMapperFacade.map(eventCommentRepository.save(commentEntity), EventComment.class));
    }

    @Override
    public boolean deleteEventComment(EventComment eventComment) {
        EventCommentEntity commentEntity = orikaMapperFacade.map(eventComment, EventCommentEntity.class);
        eventCommentRepository.delete(commentEntity);
        return true;
    }

    @Override
    public Optional<EventComment> findByEventCommentId(String eventCommentId) {
        return Optional.of(orikaMapperFacade.map(
                eventCommentRepository.findByEventCommentId(eventCommentId).get(),
                EventComment.class));
    }

    @Override
    public Set<EventComment> findByEvent(Event event) {
        EventEntity eventEntity = orikaMapperFacade.map(event, EventEntity.class);
        return orikaMapperFacade.mapAsSet(eventCommentRepository.findByEvent(eventEntity), EventComment.class);
    }
}
