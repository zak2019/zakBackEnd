package com.zak.application.service.adapter;


import com.zak.application.service.api.EventCommentService;
import com.zak.domain.exception.EventException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.Event;
import com.zak.domain.model.EventComment;
import com.zak.domain.model.User;
import com.zak.domain.spi.EventCommentPersistencePort;
import com.zak.domain.spi.EventPersistencePort;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.UserPersistencePort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventCommentServiceAdapterTest {

    @Mock
    private EventCommentPersistencePort eventCommentPersistencePort;
    @Mock
    private EventPersistencePort eventPersistencePort;
    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private IdGererator idGererator;

    @InjectMocks
    private EventCommentServiceAdapter eventCommentController;

//    @Before
//    public void setUp() throws Exception {
//        eventCommentController = new EventCommentServiceAdapter(eventPersistencePort,
//                eventCommentPersistencePort,
//                userPersistencePort);
//    }

//    @Test
//    public void should_create_event_comment_with_user_id_event_id_and_comment() {
//         given
//        String eventCommentId = "123";
//        String userId = "user1223";
//        User user = new User();
//        user.setUserId(userId);
//        String eventId = "event1223";
//        Event event = new Event();
//        event.setEventId(eventId);
//        EventComment eventComment = new EventComment();
//        eventComment.setComment("comment");
//        EventComment savedEventComment = eventComment;
//        savedEventComment.setEventCommentId(eventCommentId);
//
//         when
//        when(idGererator.generateUniqueId()).thenReturn(eventCommentId);
//        when(userPersistencePort.findUserByUserId(userId)).thenReturn(Optional.of(user));
//        when(eventPersistencePort.findByEventId(eventId)).thenReturn(Optional.of(event));
//        when(eventCommentPersistencePort.createEventComment(eventComment)).thenReturn(Optional.of(savedEventComment));
//
//        EventComment finalEventComment = eventCommentController.createEventComment(userId, eventId, eventComment).get();
//         then
//        assertEquals(finalEventComment, savedEventComment);
//        assertEquals(finalEventComment.getEventCommentId(), eventCommentId);
//    }

    @Test(expected= UserNotFoundException.class)
    public void should_throw_exception_when_create_event_comment_doesnt_find_user_with_user_id(){
        // when
        when(userPersistencePort.findUserByUserId(any())).thenReturn(Optional.empty());
        eventCommentController.createEventComment("25", "552", new EventComment());

    }

    @Test(expected= EventException.class)
    public void should_throw_exception_when_create_event_comment_doesnt_find_event_with_event_id(){
        // when
        when(userPersistencePort.findUserByUserId(any())).thenReturn(Optional.of(new User()));
        when(eventPersistencePort.findByEventId(any())).thenReturn(Optional.empty());
        eventCommentController.createEventComment("25", "552", new EventComment());

    }

}
