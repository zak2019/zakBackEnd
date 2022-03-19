package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.RetroSprintCommentGroupVoteService;
import com.zak.application.service.api.RetroSprintService;
import com.zak.application.service.api.RetroSprintWeatherService;
import com.zak.domain.exception.RetroSprintCommentGroupException;
import com.zak.domain.exception.RetroSprintCommentGroupVoteException;
import com.zak.domain.exception.RetroSprintException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class RetroSprintCommentGroupVoteServiceAdapter implements RetroSprintCommentGroupVoteService {

    private final RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort;
    private final RetroSprintCommentGroupVotePersistencePort retroSprintCommentGroupVotePersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final RetroSprintService retroSprintService;
    private final NotificationService notificationService;


    @Autowired
    private IdGererator idGererator;

    @Autowired
    public RetroSprintCommentGroupVoteServiceAdapter(RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
                                                     RetroSprintCommentGroupVotePersistencePort retroSprintCommentGroupVotePersistencePort,
                                                     UserPersistencePort userPersistencePort,
                                                     RetroSprintService retroSprintService,
                                                     NotificationService notificationService) {
        this.retroSprintCommentGroupPersistencePort = retroSprintCommentGroupPersistencePort;
        this.retroSprintCommentGroupVotePersistencePort = retroSprintCommentGroupVotePersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.retroSprintService = retroSprintService;
        this.notificationService = notificationService;
    }


    @Override
    public Optional<RetroSprintCommentGroupVote> createRetroSprintCommentGroupVote(String retroSprintId, String userId, String groupId) {

        Optional<RetroSprintCommentGroupVote> savedVote = Optional.empty();

        User user = getUser(userId);

        RetroSprintCommentGroup group = getRetroSprintCommentGroup(groupId);

        Optional<RetroSprintCommentGroupVote> vote = getRetroSprintCommentGroupVote(user, group);
        if (!vote.isPresent()){
            RetroSprintCommentGroupVote retroSprintCommentGroupVote = new RetroSprintCommentGroupVote();
            retroSprintCommentGroupVote.setRetroSprintCommentGroupVoteId(idGererator.generateUniqueId());
            retroSprintCommentGroupVote.setCreationDate(new Date());
            retroSprintCommentGroupVote.setRetroSprintCommentGroup(group);
            retroSprintCommentGroupVote.setUser(user);
            retroSprintCommentGroupVote.setVotesNumber(1);
            savedVote = retroSprintCommentGroupVotePersistencePort.createRetroSprintCommentGroupVote(retroSprintCommentGroupVote);
        } else {
            int votesNumber = vote.get().getVotesNumber();
            if (votesNumber < 3) {
                vote.get().setVotesNumber(votesNumber + 1);
                savedVote = updateRetroSprintCommentGroupVote(vote.get(), group);
            }
        }
        sendRetroSprintCommentGroupVoteAddedNotification(retroSprintId, savedVote.get(), groupId, userId);
        return savedVote;
    }

    private Optional<RetroSprintCommentGroupVote> getRetroSprintCommentGroupVote(User user, RetroSprintCommentGroup group) {
        return retroSprintCommentGroupVotePersistencePort.findByRetroSprintCommentGroupAndUser(group, user);
    }

    private RetroSprintCommentGroup getRetroSprintCommentGroup(String groupId) {
        Optional<RetroSprintCommentGroup> group = retroSprintCommentGroupPersistencePort.findByRetroSprintCommentGroupId(groupId);
        if (!group.isPresent()) {
            throw new RetroSprintCommentGroupException();
        }
        return group.get();
    }

    private User getUser(String userId) {
        Optional<User> user = userPersistencePort.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public Optional<RetroSprintCommentGroupVote> updateRetroSprintCommentGroupVote(RetroSprintCommentGroupVote vote,
                                                                                   RetroSprintCommentGroup group) {
        vote.setRetroSprintCommentGroup(group);
        return retroSprintCommentGroupVotePersistencePort.updateRetroSprintCommentGroupVote(vote);
    }

    @Override
    public Optional<RetroSprintCommentGroupVote> deleteRetroSprintCommentGroupVote(String retroSprintId, String userId, String retroSprintCommentGroupId) {
        Optional<RetroSprintCommentGroupVote> v = Optional.empty();
        User user = getUser(userId);
        RetroSprintCommentGroup group = getRetroSprintCommentGroup(retroSprintCommentGroupId);
        Optional<RetroSprintCommentGroupVote> vote = getRetroSprintCommentGroupVote(user, group);

        if (!vote.isPresent()) {
            throw new RetroSprintCommentGroupVoteException();
        } else {
            RetroSprintCommentGroupVote retroSprintCommentGroupVote = vote.get();
            int votesNumber = retroSprintCommentGroupVote.getVotesNumber();
            if (votesNumber == 3 || votesNumber == 2) {
                retroSprintCommentGroupVote.setVotesNumber(votesNumber - 1);
               v = updateRetroSprintCommentGroupVote(retroSprintCommentGroupVote, group);
            } else if (votesNumber == 1) {
                retroSprintCommentGroupVote.setVotesNumber(votesNumber - 1);
                v = Optional.of(retroSprintCommentGroupVote);
                retroSprintCommentGroupVotePersistencePort.deleteRetroSprintCommentGroupVote(retroSprintCommentGroupVote.getRetroSprintCommentGroupVoteId());
            }
            sendRetroSprintCommentGroupVoteDeletedNotification(retroSprintId, v.isPresent() ? v.get() : null, retroSprintCommentGroupId, userId);
        }
        return v;
    }

    private void sendRetroSprintCommentGroupVoteAddedNotification(String retroSprintId,
                                                                  RetroSprintCommentGroupVote vote,
                                                                  String groupId,
                                                                  String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        vote.setRetroSprintCommentGroup(new RetroSprintCommentGroup(groupId));
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupVoteAddedNotificationToUser(invitedUserId, vote);
            }
        });
    }

    private void sendRetroSprintCommentGroupVoteDeletedNotification(String retroSprintId,
                                                                  RetroSprintCommentGroupVote vote,
                                                                  String groupId,
                                                                  String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();
        vote.setRetroSprintCommentGroup(new RetroSprintCommentGroup(groupId));
        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentGroupVoteDeletedNotificationToUser(invitedUserId, vote);
            }
        });
    }

}
