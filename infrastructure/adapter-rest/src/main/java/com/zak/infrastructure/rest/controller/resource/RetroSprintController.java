package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintService;
import com.zak.domain.enums.EventStatus;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintCommentGroupDto;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint")
public class RetroSprintController {

    private final RetroSprintService retroSprintService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintController(RetroSprintService retroSprintService, MapperFacade orikaMapperFacade) {
        this.retroSprintService = retroSprintService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/{retroSprintId}")
    public RetroSprintDto getRetroSprintByRetroSprintId(@PathVariable String retroSprintId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);

        return orikaMapperFacade.map(retroSprint, RetroSprintDto.class);
    }

    @GetMapping("/team/{teamId}")
    public Set<RetroSprintDto> getRetroSprintsByTeamId(@PathVariable String teamId) {
        Set<RetroSprint> retroSprints = retroSprintService.getRetroSprintsByTeamId(teamId);

        return orikaMapperFacade.mapAsSet(retroSprints, RetroSprintDto.class);
    }

    @GetMapping("/team/{teamId}/status/{status}")
    public Set<RetroSprintDto> getRetroSprintsByTeamIdAndStatus(@PathVariable String teamId, @PathVariable EventStatus status) {
        Set<RetroSprint> retroSprints = retroSprintService.getRetroSprintsByTeamIdAnsStatus(teamId, status);

        return orikaMapperFacade.mapAsSet(retroSprints, RetroSprintDto.class);
    }

    @PostMapping("/retro-event/{retroEventId}")
    public ResponseContent<RetroSprintDto> createRetroSprint(@PathVariable String retroEventId) {
        Optional<RetroSprint> savedRetroSprint = retroSprintService.createRetroSprint(retroEventId);
        return new ResponseContent<RetroSprintDto>(
                orikaMapperFacade.map(savedRetroSprint.get(),
                        RetroSprintDto.class), "Retro sprint Created"
        );
    }

    @PostMapping("/start-board-retro")
    public ResponseContent<RetroSprintDto> startRetroSprintBoard(
            @RequestBody RetroSprintDto retroSprintDto) {
        Optional<RetroSprint> updatedRetroSprint =
                retroSprintService.startRetroSprintBoard(orikaMapperFacade.map(retroSprintDto, RetroSprint.class));
        return new ResponseContent<RetroSprintDto>(
                orikaMapperFacade.map(updatedRetroSprint.get(),
                        RetroSprintDto.class), "Retro sprint updated"
        );
    }

    @PostMapping("/{retroSprintId}/update-status/{status}")
    public ResponseContent<RetroSprintDto> updateRetroSprintStatus(
            @PathVariable String retroSprintId,
            @PathVariable EventStatus status) {
        Optional<RetroSprint> updatedRetroSprint = retroSprintService.updateRetroSprintStatus(retroSprintId, status);
        return new ResponseContent<RetroSprintDto>(
                orikaMapperFacade.map(updatedRetroSprint.get(),
                        RetroSprintDto.class), "Retro sprint Status updated to " + status
        );
    }

    @PostMapping("/{retroSprintId}/update-weather-status/{status}")
    public ResponseContent<RetroSprintDto> updateRetroSprintWeatherStatus(
            @PathVariable String retroSprintId,
            @PathVariable EventStatus status) {
        Optional<RetroSprint> updatedRetroSprint = retroSprintService.updateRetroSprintWeatherStatus(retroSprintId, status);
        return new ResponseContent<RetroSprintDto>(
                orikaMapperFacade.map(updatedRetroSprint.get(),
                        RetroSprintDto.class), "Retro sprint weather status updated to " + status
        );
    }

    @PostMapping("/{retroSprintId}/update-board-status/{status}")
    public ResponseContent<RetroSprintDto> updateRetroSprintBoardStatus(
            @PathVariable String retroSprintId,
            @PathVariable EventStatus status) {
        Optional<RetroSprint> updatedRetroSprint = retroSprintService.updateRetroSprintBoardStatus(retroSprintId, status);
        return new ResponseContent<RetroSprintDto>(
                orikaMapperFacade.map(updatedRetroSprint.get(),
                        RetroSprintDto.class), "Retro sprint board status updated to " + status
        );
    }

    @PostMapping("/{retroSprintId}/delete")
    public ResponseEntity<?> deleteRetroSprint(@PathVariable String retroSprintId) {
        retroSprintService.deleteRetroSprintByRetroSprintId(retroSprintId);
        return ResponseEntity.ok(new MessageResponseEntity("Retro sprint deleted successfully"));
    }
}
