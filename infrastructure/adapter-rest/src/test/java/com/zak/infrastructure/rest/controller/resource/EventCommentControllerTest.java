package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.EventCommentService;
import com.zak.domain.model.EventComment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import ma.glasnost.orika.MapperFacade;


@RunWith(MockitoJUnitRunner.class)
public class EventCommentControllerTest {

    @Mock
    private EventCommentService eventCommentService;
    @Mock
    MapperFacade orikaMapperFacade;

    private EventCommentController eventCommentController;

    @Before
    public void setUp() throws Exception {
        eventCommentController = new EventCommentController(eventCommentService, orikaMapperFacade);
    }

    @Test
    public void should_delete_comment_by_comment_id(){
        // given
        String idComment = "id";
        EventComment eventComment =  new EventComment();
        eventComment.setEventCommentId(idComment);
        //when
        when(eventCommentService.deleteEventCommentByEventCommentId(idComment)).thenReturn(true);
        //then
        assertTrue(eventCommentController.deleteEventCommentByEventCommentId(idComment));

    }
}
