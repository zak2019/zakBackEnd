package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.RetroSprintCommentGroupService;
import com.zak.application.service.api.UserService;
import com.zak.domain.exception.RetroSprintCommentGroupException;
import com.zak.domain.exception.RetroSprintException;
import com.zak.domain.model.RetroSprint;
import com.zak.domain.model.RetroSprintCommentGroup;
import com.zak.domain.model.RetroSprintWeather;
import com.zak.domain.model.User;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.RetroSprintCommentGroupPersistencePort;
import com.zak.domain.spi.RetroSprintPersistencePort;
import com.zak.domain.spi.UserPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class RetroSprintCommentGroupServiceAdapter implements RetroSprintCommentGroupService {

    private final RetroSprintPersistencePort retroSprintPersistencePort;
    private final RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort;
    private final NotificationService notificationService;
    private final UserService userService;
    private final IdGererator idGererator;


    @Autowired
    public RetroSprintCommentGroupServiceAdapter(RetroSprintPersistencePort retroSprintPersistencePort,
                                                 RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
                                                 NotificationService notificationService,
                                                 UserService userService,
                                                 IdGererator idGererator) {
        this.retroSprintPersistencePort = retroSprintPersistencePort;
        this.retroSprintCommentGroupPersistencePort = retroSprintCommentGroupPersistencePort;
        this.notificationService = notificationService;
        this.userService = userService;
        this.idGererator = idGererator;
    }


    @Override
    public Optional<RetroSprintCommentGroup> createRetroSprintCommentGroupWithRetroSprintId(String retroSprintId,
                                                                                            RetroSprintCommentGroup retroSprintCommentGroup) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);

        retroSprintCommentGroup.setRetroSprintCommentGroupId(idGererator.generateUniqueId());
        retroSprintCommentGroup.setName(getGroupName(retroSprintCommentGroup, retroSprint));
        retroSprintCommentGroup.setCreationDate(new Date());
        retroSprintCommentGroup.setRetroSprint(retroSprint);
        retroSprintCommentGroup.setRetroSprintCommentGroupVoteSet(Collections.EMPTY_SET);
        retroSprintCommentGroup.setRetroSprintCommentSet(Collections.EMPTY_SET);

        Optional<RetroSprintCommentGroup> savedRetroSprintCommentGroup =
                retroSprintCommentGroupPersistencePort.createRetroSprintCommentGroup(retroSprintCommentGroup);
        sendRetroSprintCommentGroupAddedNotification(savedRetroSprintCommentGroup.get(), retroSprint);
        return savedRetroSprintCommentGroup;
    }

    private String getGroupName(RetroSprintCommentGroup retroSprintCommentGroup, RetroSprint retroSprint) {
        Set<RetroSprintCommentGroup> retroSprintCommentGroupSet = retroSprint.getRetroSprintCommentGroupSet();
        int groupNumber = 1;
        groupNumber += retroSprintCommentGroupSet.stream().filter(group -> group.isGreen() == retroSprintCommentGroup.isGreen() &&
                group.isRed() == retroSprintCommentGroup.isRed() &&
                group.isYellow() == retroSprintCommentGroup.isYellow() &&
                group.isBlue() == retroSprintCommentGroup.isBlue()).count();
        return "Group " + groupNumber;
    }

    private void sendRetroSprintCommentGroupAddedNotification(RetroSprintCommentGroup retroSprintCommentGroup,
                                                              RetroSprint retroSprint) {
        String connectedUserId = userService.getConnectedUserId();
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprint.getRetroSprintId()));
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupAddedNotificationToUser(invitedUserId, retroSprintCommentGroup);
            }
        });
    }

    private void sendRetroSprintCommentGroupUpdatedNotification(RetroSprintCommentGroup retroSprintCommentGroup,
                                                                RetroSprint retroSprint) {
        String connectedUserId = userService.getConnectedUserId();
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprint.getRetroSprintId()));
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupUpdatedNotificationToUser(invitedUserId, retroSprintCommentGroup);
            }
        });
    }

    private void sendRetroSprintCommentGroupDeletedNotification(RetroSprintCommentGroup retroSprintCommentGroup,
                                                                RetroSprint retroSprint) {
        String connectedUserId = userService.getConnectedUserId();
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprint.getRetroSprintId()));
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupDeletedNotificationToUser(invitedUserId, retroSprintCommentGroup);
            }
        });
    }

    @Override
    public Optional<RetroSprintCommentGroup> createRetroSprintCommentGroupWithRetroSprint(RetroSprint retroSprint,
                                                                                          RetroSprintCommentGroup retroSprintCommentGroup) {

        retroSprintCommentGroup.setRetroSprintCommentGroupId(idGererator.generateUniqueId());
        retroSprintCommentGroup.setCreationDate(new Date());
        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprint.getId()));
        retroSprintCommentGroup.setRetroSprintCommentGroupVoteSet(Collections.EMPTY_SET);
        retroSprintCommentGroup.setRetroSprintCommentSet(Collections.EMPTY_SET);

        return retroSprintCommentGroupPersistencePort.createRetroSprintCommentGroup(retroSprintCommentGroup);
    }

    @Override
    public Optional<RetroSprintCommentGroup> updateRetroSprintCommentGroup(String retroSprintId,
                                                                           RetroSprintCommentGroup retroSprintCommentGroup) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);

        RetroSprintCommentGroup group = getRetroSprintCommentGroup(retroSprintCommentGroup.getRetroSprintCommentGroupId());

        retroSprintCommentGroup.setId(group.getId());
        retroSprintCommentGroup.setRetroSprint(retroSprint);
        retroSprintCommentGroup.setRetroSprintCommentSet(Collections.EMPTY_SET);
        retroSprintCommentGroup.setRetroSprintCommentGroupVoteSet(Collections.EMPTY_SET);
        Optional<RetroSprintCommentGroup> saved = retroSprintCommentGroupPersistencePort.updateRetroSprintCommentGroup(retroSprintCommentGroup);
        sendRetroSprintCommentGroupUpdatedNotification(saved.get(), retroSprint);
        return saved;
    }

    @Override
    public Set<RetroSprintCommentGroup> startVoteRetroSprintCommentGroup(RetroSprint retroSprint) {
        return updateRetroSprintCommentGroupsVoteStarted(retroSprint, true);
    }

    private Set<RetroSprintCommentGroup> updateRetroSprintCommentGroupsVoteStarted(RetroSprint retroSprint, boolean voteStarted) {
        RetroSprint dbRetroSprint = getRetroSprint(retroSprint.getRetroSprintId());
        Set<RetroSprintCommentGroup> updatedSet = new HashSet<>();
        retroSprint.getRetroSprintCommentGroupSet().forEach(group -> {
            RetroSprintCommentGroup retroSprintCommentGroup = getRetroSprintCommentGroup(group.getRetroSprintCommentGroupId());
            retroSprintCommentGroup.setVoteStarted(voteStarted);
            retroSprintCommentGroup.setRetroSprint(new RetroSprint(dbRetroSprint.getId()));
            retroSprintCommentGroup.setRetroSprintCommentSet(Collections.EMPTY_SET);
            retroSprintCommentGroup.setRetroSprintCommentGroupVoteSet(Collections.EMPTY_SET);
            updatedSet.add(retroSprintCommentGroupPersistencePort.updateRetroSprintCommentGroup(retroSprintCommentGroup).get());
        });
        sendRetroSprintCommentGroupsVoteStartedUpdatedNotification(updatedSet, dbRetroSprint);
        return updatedSet;
    }


    @Override
    public Set<RetroSprintCommentGroup> cancelVoteRetroSprintCommentGroup(RetroSprint retroSprint) {
        return updateRetroSprintCommentGroupsVoteStarted(retroSprint, false);
    }

    private void sendRetroSprintCommentGroupsVoteStartedUpdatedNotification(Set<RetroSprintCommentGroup> retroSprintCommentGroups,
                                                                            RetroSprint retroSprint) {
        String connectedUserId = userService.getConnectedUserId();
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        retroSprintCommentGroups.forEach(group -> {
            group.setRetroSprint(new RetroSprint(retroSprint.getRetroSprintId()));
        });
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupVoteStartedUpdatedNotificationToUser(invitedUserId, retroSprintCommentGroups);
            }
        });
    }

    private RetroSprint getRetroSprint(String retroSprintId) {
        Optional<RetroSprint> retroSprint = retroSprintPersistencePort.findByRetroSprintId(retroSprintId);
        if (!retroSprint.isPresent()) {
            throw new RetroSprintException("Retro sprint not found");
        }
        return retroSprint.get();
    }

    @Override
    public boolean deleteRetroSprintCommentGroupByRetroSprintCommentGroupId(String retroSprintId, String retroSprintCommentGroupId) {

        RetroSprint retroSprint = getRetroSprint(retroSprintId);
        RetroSprintCommentGroup retroSprintCommentGroup = getRetroSprintCommentGroup(retroSprintCommentGroupId);

        retroSprintCommentGroupPersistencePort.deleteRetroSprintCommentGroup(retroSprintCommentGroup);
        sendRetroSprintCommentGroupDeletedNotification(retroSprintCommentGroup, retroSprint);
        return true;
    }

    private RetroSprintCommentGroup getRetroSprintCommentGroup(String retroSprintCommentGroupId) {
        Optional<RetroSprintCommentGroup> retroSprintCommentGroup =
                retroSprintCommentGroupPersistencePort.findByRetroSprintCommentGroupId(retroSprintCommentGroupId);
        if (!retroSprintCommentGroup.isPresent()) {
            throw new RetroSprintCommentGroupException();
        }
        return retroSprintCommentGroup.get();
    }

}
