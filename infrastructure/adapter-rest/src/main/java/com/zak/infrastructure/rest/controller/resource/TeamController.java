package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.TeamService;
import com.zak.domain.model.Team;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.resource.dto.TeamDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/team")
public class TeamController {

    private final TeamService teamService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public TeamController(TeamService teamService,
                          MapperFacade orikaMapperFacade) {
        this.teamService = teamService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/{teamId}")
    public TeamDto getTeamByTeamId(@PathVariable final String teamId) {
        Team team = teamService.getTeamByTeamId(teamId);
        return orikaMapperFacade.map(team, TeamDto.class);
    }

    @GetMapping("/account/{accountId}")
    public Set<TeamDto> getTeamsByAccountId(@PathVariable final String accountId) {
        Set<Team> teamsList = teamService.getTeamsByAccountId(accountId);
        return orikaMapperFacade.mapAsSet(teamsList, TeamDto.class);
    }

    @PostMapping("/create-team/{accountId}")
    public ResponseContent<?> createTeam(@PathVariable final String accountId,
                                                  @RequestBody final TeamDto teamDto) {
        Team savedTeam = teamService.createTeam(
                accountId,
                orikaMapperFacade.map(teamDto, Team.class
                ));
        return new ResponseContent(orikaMapperFacade.map(savedTeam, TeamDto.class),
                savedTeam.getTeamName() + " team added successfully");
    }

    @PostMapping("/{teamId}/user/{userId}")
    public ResponseContent<?> linkUserToTeam(@PathVariable final String teamId,
                                             @PathVariable final String userId) {
        Team savedTeam = teamService.addUserToTeam(
                userId,
                teamId
                );
        return new ResponseContent(orikaMapperFacade.map(savedTeam, TeamDto.class),
                "User added successfully");
    }

    @PostMapping("/link-users")
    public ResponseContent<?> linkUsersToTeam(@RequestBody final TeamDto teamDto) {
        Team savedTeam = teamService.linkUsersToTeam(orikaMapperFacade.map(teamDto, Team.class));
        return new ResponseContent(orikaMapperFacade.map(savedTeam, TeamDto.class),
                getLinkUsersToTeamMessage(teamDto));
    }

    private String getLinkUsersToTeamMessage(TeamDto teamDto) {
        int linkedUsersNumber = teamDto.getLinkedUsers().size();
        return linkedUsersNumber == 1 ? "User linked successfully" : linkedUsersNumber + " Users linked successfully";
    }

    @PostMapping("/user/{userId}/account/{accountId}")
    public ResponseContent<?> linkUserToNewTeam(@PathVariable final String accountId,
                                                @PathVariable final String userId,
                                                @RequestBody final TeamDto teamDto) {
        Team savedTeam = teamService.addUserToNewTeam(
                userId,
                accountId,
                orikaMapperFacade.map(teamDto, Team.class)
                );
        return new ResponseContent(orikaMapperFacade.map(savedTeam, TeamDto.class),
                "Created successfully");
    }
}
