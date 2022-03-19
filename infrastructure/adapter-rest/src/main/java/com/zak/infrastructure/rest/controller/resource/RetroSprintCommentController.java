package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintCommentService;
import com.zak.domain.model.DragDropRetroSprintComment;
import com.zak.domain.model.RetroSprintComment;
import com.zak.infrastructure.rest.controller.resource.dto.DragDropRetroSprintCommentDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint-comment")
public class RetroSprintCommentController {

    private final RetroSprintCommentService retroSprintCommentService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintCommentController(RetroSprintCommentService retroSprintCommentService,
                                        MapperFacade orikaMapperFacade) {
        this.retroSprintCommentService = retroSprintCommentService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/retro-sprint/{retroSprintId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/user/{userId}")
    public RetroSprintCommentDto createRetroSprintComment(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String userId,
            @RequestBody RetroSprintCommentDto retroSprintCommentDto) {
        Optional<RetroSprintComment> savedRetroSprintComment =
                retroSprintCommentService.createRetroSprintComment(
                        retroSprintId,
                        userId,
                        retroSprintCommentGroupId,
                        orikaMapperFacade.map(retroSprintCommentDto, RetroSprintComment.class));
        return orikaMapperFacade.map(savedRetroSprintComment.get(), RetroSprintCommentDto.class);
    }

    @PostMapping("/retro-sprint-comment-group/{retroSprintCommentGroupId}/retro-sprint/{retroSprintId}/update")
    public RetroSprintCommentDto updateRetroSprintComment(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @RequestBody RetroSprintCommentDto retroSprintCommentDto) {
        Optional<RetroSprintComment> savedRetroSprintComment =
                retroSprintCommentService.updateRetroSprintComment(
                        retroSprintId,
                        retroSprintCommentGroupId,
                        orikaMapperFacade.map(retroSprintCommentDto, RetroSprintComment.class));
        return orikaMapperFacade.map(savedRetroSprintComment.get(), RetroSprintCommentDto.class);
    }

    @GetMapping("/retro-sprint-comment-group/{retroSprintCommentGroupId}")
    public Set<RetroSprintCommentDto> getCommentsByRetroSprintCommentGroupId( @PathVariable String retroSprintCommentGroupId) {
        Set<RetroSprintComment> retroSprintsCommentSet =
                retroSprintCommentService.getCommentsByRetroSprintCommentGroupId(retroSprintCommentGroupId);
        return orikaMapperFacade.mapAsSet(retroSprintsCommentSet, RetroSprintCommentDto.class);
    }

    @PostMapping("/{retroSprintCommentId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/retro-sprint/{retroSprintId}/to-position/{toPosition}")
    public DragDropRetroSprintCommentDto changeRetroSprintsCommentPosition(
            @PathVariable String retroSprintCommentId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String retroSprintId,
            @PathVariable int toPosition) {
        DragDropRetroSprintComment dragDropRetroSprintComment =
                retroSprintCommentService.changeRetroSprintCommentPosition(retroSprintCommentId, retroSprintCommentGroupId, retroSprintId, toPosition);
        return orikaMapperFacade.map(dragDropRetroSprintComment, DragDropRetroSprintCommentDto.class);
    }

    @PostMapping("/{retroSprintCommentId}/retro-sprint/{retroSprintId}/from-group/{fromGroupId}/to-group/{toGroupId}/to-position/{toPosition}")
    public DragDropRetroSprintCommentDto changeRetroSprintsCommentPositionAndGroup(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentId,
            @PathVariable String fromGroupId,
            @PathVariable String toGroupId,
            @PathVariable int toPosition) {
        DragDropRetroSprintComment dragDropRetroSprintComment   =
                retroSprintCommentService.changeRetroSprintCommentPositionAndGroup(
                        retroSprintId,
                        retroSprintCommentId,
                        fromGroupId,
                        toGroupId,
                        toPosition);
        return orikaMapperFacade.map(dragDropRetroSprintComment, DragDropRetroSprintCommentDto.class);
    }

    @PostMapping("/{retroSprintCommentId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/retro-sprint/{retroSprintId}/delete")
    public Set<RetroSprintCommentDto> deleteRetroSprintCommentByRetroSprintCommentId(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentId,
            @PathVariable String retroSprintCommentGroupId
            ) {
        Set<RetroSprintComment> updatedComments = retroSprintCommentService
                .deleteRetroSprintCommentByRetroSprintCommentIdAndGroupId(retroSprintCommentId, retroSprintCommentGroupId, retroSprintId);
        return orikaMapperFacade.mapAsSet(updatedComments,RetroSprintCommentDto.class);
    }
}
