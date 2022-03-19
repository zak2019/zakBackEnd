package com.zak.domain.spi;

import com.zak.domain.model.Event;
import com.zak.domain.model.EventComment;

import java.util.Optional;
import java.util.Set;

public interface EventCommentPersistencePort {

    Optional<EventComment> createEventComment(EventComment eventComment);
    Optional<EventComment> updateEventComment(EventComment eventComment);
    boolean deleteEventComment(EventComment eventComment);
    Optional<EventComment> findByEventCommentId(String eventCommentId);
    Set<EventComment> findByEvent(Event event);
}
