package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintCommentGroupVoteService;
import com.zak.application.service.api.RetroSprintCommentVoteService;
import com.zak.domain.model.RetroSprintCommentGroupVote;
import com.zak.domain.model.RetroSprintCommentVote;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentGroupVoteDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentVoteDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint-comment-vote")
public class RetroSprintCommentVoteController {

    private final RetroSprintCommentVoteService retroSprintCommentVoteService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintCommentVoteController(RetroSprintCommentVoteService retroSprintCommentVoteService,
                                            MapperFacade orikaMapperFacade) {
        this.retroSprintCommentVoteService = retroSprintCommentVoteService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/retro-sprint/{retroSprintId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/retro-sprint-comment/{retroSprintCommentId}/user/{userId}")
    public RetroSprintCommentVoteDto createRetroSprintCommentGroupVote(
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String retroSprintCommentId,
            @PathVariable String userId) {
        RetroSprintCommentVote savedVote =
                retroSprintCommentVoteService.createRetroSprintCommentVote(
                        userId,
                        retroSprintCommentId,
                        retroSprintCommentGroupId,
                        retroSprintId).get();

        return orikaMapperFacade.map(savedVote, RetroSprintCommentVoteDto.class);
    }

    @PostMapping("/{retroSprintCommentVoteId}/retro-sprint/{retroSprintId}/retro-sprint-comment-group/{retroSprintCommentGroupId}/retro-sprint-comment/{retroSprintCommentId}/delete")
    public RetroSprintCommentVoteDto removeRetroSprintCommentVote(
            @PathVariable String retroSprintCommentVoteId,
            @PathVariable String retroSprintId,
            @PathVariable String retroSprintCommentGroupId,
            @PathVariable String retroSprintCommentId) {
        Optional<RetroSprintCommentVote> retroSprintCommentVote =
                retroSprintCommentVoteService.deleteRetroSprintCommentVote(retroSprintCommentVoteId, retroSprintCommentId, retroSprintCommentGroupId, retroSprintId);
        if (retroSprintCommentVote.isPresent()) {
            return orikaMapperFacade.map(retroSprintCommentVote.get(), RetroSprintCommentVoteDto.class);
        }
        return null;
    }
}
