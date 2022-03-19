package com.zak.application.service.adapter;

import com.zak.application.service.api.EventCommentService;
import com.zak.application.service.api.UserActivityService;
import com.zak.domain.exception.EventCommentException;
import com.zak.domain.exception.EventException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class EventCommentServiceAdapter implements EventCommentService {

    private final EventCommentPersistencePort eventCommentPersistencePort;
    private final EventPersistencePort eventPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final UserActivityService userActivityService;
    private final IdGererator idGererator;


    @Autowired
    public EventCommentServiceAdapter(EventPersistencePort eventPersistencePort,
                                      EventCommentPersistencePort eventCommentPersistencePort,
                                      UserPersistencePort userPersistencePort,
                                      UserActivityService userActivityService,
                                      IdGererator idGererator) {
        this.eventPersistencePort = eventPersistencePort;
        this.eventCommentPersistencePort = eventCommentPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.userActivityService = userActivityService;
        this.idGererator = idGererator;
    }


    @Override
    @Transactional
    public Optional<EventComment> createEventComment(String userId, String eventId, EventComment eventComment) {

        Optional<User> user = userPersistencePort.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        if (!event.isPresent()) {
            throw new EventException("Event not found");
        }


        eventComment.setEventCommentId(idGererator.generateUniqueId());
        eventComment.setCreationDate(new Date());
        eventComment.setEvent(event.get());
        eventComment.setUser(user.get());

        Optional<EventComment> savedEventComment =
                eventCommentPersistencePort.createEventComment(eventComment);
        if (savedEventComment.isPresent()) {
            setAndSaveUserActivityAddComment(user.get(), savedEventComment.get(), event.get());
        }
        return savedEventComment;
    }

    private Optional<UserActivity> setAndSaveUserActivityAddComment(User user, EventComment eventComment, Event event) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUser(user);
        userActivity.setCreationDate(new Date());
        userActivity.setUserActivityId(idGererator.generateUniqueId());
        userActivity.setEventComment(eventComment);
        userActivity.setEvent(event);
        return userActivityService.createUserActivity(user, userActivity);
    }

    @Override
    public Optional<EventComment> updateEventComment(EventComment eventComment) {
        return eventCommentPersistencePort.updateEventComment(eventComment);
    }

    @Override
    public boolean deleteEventCommentByEventCommentId(String eventCommentId) {

        String connectedUserSecretId = userPersistencePort.getConnectedUserSecretId();
        Optional<EventComment> eventComment = eventCommentPersistencePort.findByEventCommentId(eventCommentId);
        if (!eventComment.isPresent()) {
            throw new EventCommentException("Event comment not found");
        }

        if (!connectedUserSecretId.equals(eventComment.get().getUser().getSecretId())) {
            throw new EventCommentException("You can't delete this comment");
        }
        eventCommentPersistencePort.deleteEventComment(eventComment.get());
        return true;
    }

    @Override
    public Set<EventComment> getEventsCommentsByEventId(String eventId) {

        Optional<Event> event = eventPersistencePort.findByEventId(eventId);
        if (!event.isPresent()) {
            throw new EventCommentException("Event not found");
        }


        return eventCommentPersistencePort.findByEvent(event.get());
    }
}
