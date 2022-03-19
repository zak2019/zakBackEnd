package com.zak.application.service.api;

import com.zak.domain.model.EventComment;
import com.zak.domain.model.EventWeather;

import java.util.Optional;
import java.util.Set;

public interface EventCommentService {
    Optional<EventComment> createEventComment(String userId, String eventId, EventComment eventComment);
    Optional<EventComment> updateEventComment(EventComment eventComment);
    boolean deleteEventCommentByEventCommentId(String eventCommentId);
    Set<EventComment> getEventsCommentsByEventId(String eventId);
}
