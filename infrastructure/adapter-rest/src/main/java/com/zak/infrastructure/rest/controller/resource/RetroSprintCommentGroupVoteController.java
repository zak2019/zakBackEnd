package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintCommentGroupVoteService;
import com.zak.application.service.api.RetroSprintWeatherService;
import com.zak.domain.model.RetroSprintCommentGroupVote;
import com.zak.domain.model.RetroSprintWeather;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.resource.dto.EventDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentGroupVoteDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintWeatherDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint-comment-group-vote")
public class RetroSprintCommentGroupVoteController {

    private final RetroSprintCommentGroupVoteService retroSprintCommentGroupVoteService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintCommentGroupVoteController(RetroSprintCommentGroupVoteService retroSprintCommentGroupVoteService,
                                                 MapperFacade orikaMapperFacade) {
        this.retroSprintCommentGroupVoteService = retroSprintCommentGroupVoteService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("retro-sprint/{retroSprintId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/user/{userId}")
    public ResponseContent<RetroSprintCommentGroupVoteDto> createRetroSprintCommentGroupVote(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String userId) {
        Optional<RetroSprintCommentGroupVote> savedVote =
                retroSprintCommentGroupVoteService.createRetroSprintCommentGroupVote(
                        retroSprintId,
                        userId,
                        retroSprintCommentGroupId);
        return new ResponseContent<RetroSprintCommentGroupVoteDto>(
                savedVote.isPresent() ? orikaMapperFacade.map(savedVote.get(), RetroSprintCommentGroupVoteDto.class) : null,
                savedVote.isPresent() ? "Vote added successfully" : "You can't vote more than 3 times"
        );
    }

    @PostMapping("retro-sprint/{retroSprintId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/user/{userId}/remove")
    public ResponseContent<RetroSprintCommentGroupVoteDto> removeRetroSprintCommentGroupVote(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String userId) {
        Optional<RetroSprintCommentGroupVote> savedVote =
                retroSprintCommentGroupVoteService.deleteRetroSprintCommentGroupVote(
                        retroSprintId,
                        userId,
                        retroSprintCommentGroupId);
        return new ResponseContent<RetroSprintCommentGroupVoteDto>(
                savedVote.isPresent() ? orikaMapperFacade.map(savedVote.get(), RetroSprintCommentGroupVoteDto.class) : null,
                "Retro Sprint comment group vote removed"
        );
    }
}
