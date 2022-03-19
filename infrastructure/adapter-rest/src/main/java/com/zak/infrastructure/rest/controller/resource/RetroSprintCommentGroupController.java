package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintCommentGroupService;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentGroupDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint-comment-group")
public class RetroSprintCommentGroupController {

    private final RetroSprintCommentGroupService retroSprintCommentGroupService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintCommentGroupController(RetroSprintCommentGroupService retroSprintCommentGroupService,
                                             MapperFacade orikaMapperFacade) {
        this.retroSprintCommentGroupService = retroSprintCommentGroupService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/retro-sprint/{retroSprintId}")
    public RetroSprintCommentGroupDto createRetroSprintCommentGroup(
            @PathVariable String retroSprintId,
            @RequestBody RetroSprintCommentGroupDto retroSprintCommentGroupDto) {
        Optional<RetroSprintCommentGroup> savedRetroSprintCommentGroup =
                retroSprintCommentGroupService.createRetroSprintCommentGroupWithRetroSprintId(
                        retroSprintId,
                        orikaMapperFacade.map(retroSprintCommentGroupDto, RetroSprintCommentGroup.class));
        return orikaMapperFacade.map(savedRetroSprintCommentGroup.get(), RetroSprintCommentGroupDto.class);
    }

    @PostMapping("/retro-sprint/{retroSprintId}/update")
    public RetroSprintCommentGroupDto updateRetroSprintCommentGroup(
            @PathVariable String retroSprintId,
            @RequestBody RetroSprintCommentGroupDto retroSprintCommentGroupDto) {
        Optional<RetroSprintCommentGroup> savedRetroSprintCommentGroup =
                retroSprintCommentGroupService.updateRetroSprintCommentGroup(retroSprintId,
                        orikaMapperFacade.map(retroSprintCommentGroupDto, RetroSprintCommentGroup.class));
        return orikaMapperFacade.map(savedRetroSprintCommentGroup.get(), RetroSprintCommentGroupDto.class);
    }

    @PostMapping("/start-board-retro-vote-for-groups")
    public Set<RetroSprintCommentGroupDto> startRetroSprintBoardVote(
            @RequestBody RetroSprintDto retroSprintDto) {
        Set<RetroSprintCommentGroup> updatedRetroSprintCommentGroupSet =
                retroSprintCommentGroupService.startVoteRetroSprintCommentGroup(orikaMapperFacade.map(retroSprintDto, RetroSprint.class));
        return orikaMapperFacade.mapAsSet(updatedRetroSprintCommentGroupSet, RetroSprintCommentGroupDto.class);
    }


    @PostMapping("/cancel-board-retro-vote-for-groups")
    public Set<RetroSprintCommentGroupDto> cancelRetroSprintBoardVote(
            @RequestBody RetroSprintDto retroSprintDto) {
        Set<RetroSprintCommentGroup> updatedRetroSprintCommentGroupSet =
                retroSprintCommentGroupService.cancelVoteRetroSprintCommentGroup(orikaMapperFacade.map(retroSprintDto, RetroSprint.class));
        return orikaMapperFacade.mapAsSet(updatedRetroSprintCommentGroupSet, RetroSprintCommentGroupDto.class);
    }

//    @GetMapping("/event/{eventId}")
//    public Set<EventCommentDto> getEventsCommentsByEventId( @PathVariable String eventId) {
//        Set<EventComment> eventsCommentSet =
//                eventCommentService.getEventsCommentsByEventId(eventId);
//        return orikaMapperFacade.mapAsSet(eventsCommentSet, EventCommentDto.class);
//    }

    @PostMapping("/{retroSprintCommentGroupId}/retro-sprint/{retroSprintId}/delete")
    public boolean deletesRetroSprintCommentGroupByRetroSprintCommentGroupId( @PathVariable String retroSprintCommentGroupId,
                                                                              @PathVariable String retroSprintId) {
        return retroSprintCommentGroupService.deleteRetroSprintCommentGroupByRetroSprintCommentGroupId(retroSprintId, retroSprintCommentGroupId);
    }
}
