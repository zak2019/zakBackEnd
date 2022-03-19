package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.enums.EventStatus;
import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.exception.EventException;
import com.zak.domain.exception.RetroSprintCommentGroupException;
import com.zak.domain.exception.RetroSprintException;
import com.zak.domain.exception.TeamException;
import com.zak.domain.model.*;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.RetroSprintPersistencePort;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class RetroSprintServiceAdapter implements RetroSprintService {

    private final RetroSprintPersistencePort retroSprintPersistencePort;
    private final RetroSprintCommentGroupService retroSprintCommentGroupService;
    private final EventService eventService;
    private final TeamService teamService;
    private final UserService userService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Autowired
    private IdGererator idGererator;

    public RetroSprintServiceAdapter(RetroSprintPersistencePort retroSprintPersistencePort,
                                     RetroSprintCommentGroupService retroSprintCommentGroupService,
                                     EventService eventService,
                                     TeamService teamService,
                                     UserService userService,
                                     EmailService emailService,
                                     NotificationService notificationService) {
        this.retroSprintPersistencePort = retroSprintPersistencePort;
        this.retroSprintCommentGroupService = retroSprintCommentGroupService;
        this.eventService = eventService;
        this.teamService = teamService;
        this.userService = userService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @Override
    public RetroSprint getRetroSprintByRetroSprintId(String retroSprintId) {
        Optional<RetroSprint> retroSprint = retroSprintPersistencePort.findByRetroSprintId(retroSprintId);
        if (!retroSprint.isPresent()) {
            throw new RetroSprintException("Retro Sprint not found");
        }
        return retroSprint.get();
    }


    @Override
    public Set<RetroSprint> getRetroSprintsByTeamId(String teamId) {
        Team team = getAndVerifyTeam(teamId);
        Set<RetroSprint> retroSprints = retroSprintPersistencePort.findByTeam(team);
        return retroSprints;
    }

    @Override
    public Set<RetroSprint> getRetroSprintsByTeamIdAnsStatus(String teamId, EventStatus status) {
        Team team = getAndVerifyTeam(teamId);
        Set<RetroSprint> retroSprints = retroSprintPersistencePort.findByTeamAndStatus(team, status);
        return retroSprints;
    }

    private Team getAndVerifyTeam(String teamId) {
        Team team = teamService.getTeamByTeamId(teamId);
        if (team == null) {
            throw new TeamException();
        }
        return team;
    }

    @Transactional
    @Override
    public Optional<RetroSprint> createRetroSprint(String retroEventId) {
        RetroSprint retroSprint = new RetroSprint();
        String connectedUserId = userService.getConnectedUserId();

        Event event = eventService.getEventByEventId(retroEventId);
        if (event == null) {
            throw new EventException("Event not found");
        }

        retroSprint.setCreatedBy(userService.findUserByUserId(connectedUserId).get());
        retroSprint.setEventRetro(event);
        retroSprint.setCreationDate(new Date());
        retroSprint.setRetroSprintId(idGererator.generateUniqueId());
        retroSprint.setRetroName("Retro " + event.getTeam().getTeamName());
        retroSprint.setStatus(EventStatus.IN_PROGRESS);
        retroSprint.setWeatherStatus(EventStatus.IN_PROGRESS);
        retroSprint.setBoardStatus(EventStatus.IN_PROGRESS);
        retroSprint.setRetroSprintCommentGroupSet(Collections.EMPTY_SET);
        retroSprint.setRetroSprintWeatherSet(Collections.EMPTY_SET);
        Optional<RetroSprint> savedRetroSprint = retroSprintPersistencePort.saveRetroSprint(retroSprint);

        event.setStartedAt(new Date());
        eventService.updateEvent(event);

        if (savedRetroSprint.isPresent()) {
            notifyUsers(event, savedRetroSprint.get());
        }
        return savedRetroSprint;
    }

    private void notifyUsers(Event event,  RetroSprint retroSprint) {
        createAndNotifyUsers(event, NotificationTypeEnum.EVENT_STARTED);
        notifyUsersByMailWhenRetroSprintStarted(event);
    }

    private void createAndNotifyUsers(Event event, NotificationTypeEnum type) {
        Set<User> users = event.getUsers();
        users.forEach(user -> {
            Notification notification = new Notification();
            notification.setUserToNotify(user);
            notification.setEvent(event);
            notificationService.sendNotificationToUser(notification, type);
        });
    }

    @Override
    public Optional<RetroSprint> updateRetroSprintStatus(String retroSprintId, EventStatus status) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);
        retroSprint.setStatus(status);
        setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(retroSprint);
        Optional<RetroSprint> updatedRetro = retroSprintPersistencePort.saveRetroSprint(retroSprint);
        if (updatedRetro.isPresent()) {
            sendUpdatedRetroSprintStatusNotification(updatedRetro.get());
        }
        return updatedRetro;
    }

    @Override
    @Transactional
    public Optional<RetroSprint> startRetroSprintBoard(RetroSprint retroSprint) {
        Optional<RetroSprint> savedRetroSprint = Optional.empty();

        RetroSprint dbRetroSprint = getRetroSprint(retroSprint.getRetroSprintId());
        retroSprint.setId(dbRetroSprint.getId());
        retroSprint.setRetroSprintWeatherSet(dbRetroSprint.getRetroSprintWeatherSet());
        Set<RetroSprintCommentGroup> retroSprintCommentGroups = createRetroSprintCommentGroups(retroSprint);
        retroSprint.setRetroSprintCommentGroupSet(retroSprintCommentGroups);
        setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(retroSprint);

        if (retroSprintCommentGroups.size() > 0) {
            savedRetroSprint = retroSprintPersistencePort.saveRetroSprint(retroSprint);
        } else {
            throw new RetroSprintCommentGroupException("You can't start retro board");
        }

        sendUpdatedRetroSprintStatusNotification(savedRetroSprint.get());

        return savedRetroSprint;
    }

    private Set<RetroSprintCommentGroup> createRetroSprintCommentGroups(RetroSprint retroSprint) {
        Optional<RetroSprintCommentGroup> blueGroup = Optional.empty();
        Optional<RetroSprintCommentGroup> greenGroup = Optional.empty();
        Optional<RetroSprintCommentGroup> redGroup = Optional.empty();
        Optional<RetroSprintCommentGroup> yellowGroup = Optional.empty();
        Set<RetroSprintCommentGroup> groupList = new HashSet<>();
        if (retroSprint.isBlue()) {
            RetroSprintCommentGroup retroSprintCommentGroup = new RetroSprintCommentGroup();
            retroSprintCommentGroup.setBlue(true);
            blueGroup =
                    retroSprintCommentGroupService.createRetroSprintCommentGroupWithRetroSprint(retroSprint, retroSprintCommentGroup);
            groupList.add(blueGroup.get());
        }
        if (retroSprint.isGreen()) {
            RetroSprintCommentGroup retroSprintCommentGroup = new RetroSprintCommentGroup();
            retroSprintCommentGroup.setGreen(true);
            greenGroup =
                    retroSprintCommentGroupService.createRetroSprintCommentGroupWithRetroSprint(retroSprint, retroSprintCommentGroup);
            groupList.add(greenGroup.get());
        }
        if (retroSprint.isRed()) {
            RetroSprintCommentGroup retroSprintCommentGroup = new RetroSprintCommentGroup();
            retroSprintCommentGroup.setRed(true);
            redGroup =
                    retroSprintCommentGroupService.createRetroSprintCommentGroupWithRetroSprint(retroSprint, retroSprintCommentGroup);
            groupList.add(redGroup.get());
        }
        if (retroSprint.isYellow()) {
            RetroSprintCommentGroup retroSprintCommentGroup = new RetroSprintCommentGroup();
            retroSprintCommentGroup.setYellow(true);
            yellowGroup =
                    retroSprintCommentGroupService.createRetroSprintCommentGroupWithRetroSprint(retroSprint, retroSprintCommentGroup);
            groupList.add(yellowGroup.get());
        }
        return groupList;
    }

    @Override
    public Optional<RetroSprint> updateRetroSprint(RetroSprint retroSprint) {
        RetroSprint dbRetroSprint = getRetroSprint(retroSprint.getRetroSprintId());
        retroSprint.setId(dbRetroSprint.getId());
        setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(retroSprint);
        return retroSprintPersistencePort.saveRetroSprint(dbRetroSprint);
    }


    @Transactional
    @Override
    public Optional<RetroSprint> updateRetroSprintWeatherStatus(String retroSprintId, EventStatus status) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);
        retroSprint.setWeatherStatus(status);
        setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(retroSprint);
        Optional<RetroSprint> updatedRetroSprint = retroSprintPersistencePort.saveRetroSprint(retroSprint);
        if (updatedRetroSprint.isPresent()) {
            sendUpdatedRetroSprintStatusNotification(updatedRetroSprint.get());
        }

        return updatedRetroSprint;
    }

    private void sendUpdatedRetroSprintStatusNotification(RetroSprint retroSprint) {
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        String connectedUserId = userService.getConnectedUserId();
        invitedUsers.forEach( invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintNotificationToUser(invitedUserId, retroSprint);
            }
        });
    }


    @Override
    public Optional<RetroSprint> updateRetroSprintBoardStatus(String retroSprintId, EventStatus status) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);
        retroSprint.setBoardStatus(status);
//        if (EventStatus.COMPLETED.equals(status)) {
//        retroSprint.setStatus(status);
//        }
        setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(retroSprint);
        Optional<RetroSprint> updatedRetroSprint = retroSprintPersistencePort.saveRetroSprint(retroSprint);

        sendUpdatedRetroSprintStatusNotification(updatedRetroSprint.get());

        return updatedRetroSprint;
    }

    // to do ...
    private void setRetroSprintInRetroSprintsWeatherAndCommentGroupSet(RetroSprint retro) {
        if (retro.getRetroSprintWeatherSet() != null) {
            retro.getRetroSprintWeatherSet().stream()
                    .forEach(weather -> weather.setRetroSprint(new RetroSprint(retro.getId())));
        }
        if (retro.getRetroSprintCommentGroupSet() != null) {
            retro.getRetroSprintCommentGroupSet().stream()
                    .forEach(group -> {
                        group.setRetroSprint(new RetroSprint(retro.getId()));
                        if (group.getRetroSprintCommentGroupVoteSet() != null) {
                            group.getRetroSprintCommentGroupVoteSet().stream()
                                    .forEach(groupVote -> groupVote.setRetroSprintCommentGroup(new RetroSprintCommentGroup(group.getId())));
                            if (group.getRetroSprintCommentSet() != null) {
                                group.getRetroSprintCommentSet().stream()
                                        .forEach(comment -> {
                                            comment.setRetroSprintCommentGroup(new RetroSprintCommentGroup(group.getId()));
                                            comment.getRetroSprintCommentVoteSet().stream()
                                                    .forEach(vote -> vote.setRetroSprintComment(new RetroSprintComment(comment.getId())));
                                        });
                            }
                        }
                    });
        }
    }

    private RetroSprint getRetroSprint(String retroSprintId) {
        Optional<RetroSprint> retroSprint = retroSprintPersistencePort.findByRetroSprintId(retroSprintId);
        if (!retroSprint.isPresent()) {
            throw new RetroSprintException();
        }
        return retroSprint.get();
    }

    @Override
    public boolean deleteRetroSprintByRetroSprintId(String retroSprintId) {
        RetroSprint existingRetroSprint = getRetroSprint(retroSprintId);

        boolean deleteResponse = this.retroSprintPersistencePort.deleteRetroSprintByRetroSprintId(existingRetroSprint);
        return deleteResponse;
    }

    private void notifyUsersByMailWhenRetroSprintStarted(Event event) {

        event.getUsers().forEach(user -> {
            emailService.sendEmail(
                    user.getEmail(),
                    "Retro started",
                    "<h3>" + event.getAccount().getAccountName() + " project</h1>" +
                            "<p>The event retro that you are affected to has been started: <br>" +
                            "Event name: " + event.getName() + "<br>" +
                            "Event Project: " + event.getAccount().getAccountName() + "<br>" +
                            (event.getTeam() != null ? "Event Team: " + event.getTeam().getTeamName() + "<br>" : "") +
                            "Event retro url: http://localhost:4200/account/" + event.getAccount().getAccountId() + "/admin/reports <br>" +
                            "</p>"
            );
        });
    }
}
