package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.RetroSprintCommentVoteService;
import com.zak.application.service.api.RetroSprintService;
import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.domain.exception.RetroSprintCommentException;
import com.zak.domain.exception.RetroSprintCommentGroupException;
import com.zak.domain.exception.RetroSprintCommentVoteException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class RetroSprintCommentVoteServiceAdapter implements RetroSprintCommentVoteService {

    private final RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort;
    private final RetroSprintCommentPersistencePort retroSprintCommentPersistencePort;
    private final RetroSprintCommentVotePersistencePort retroSprintCommentVotePersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final RetroSprintService retroSprintService;
    private final NotificationService notificationService;


    @Autowired
    private IdGererator idGererator;

    @Autowired
    public RetroSprintCommentVoteServiceAdapter(RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
                                                RetroSprintCommentPersistencePort retroSprintCommentPersistencePort,
                                                RetroSprintCommentVotePersistencePort retroSprintCommentVotePersistencePort,
                                                UserPersistencePort userPersistencePort,
                                                RetroSprintService retroSprintService,
                                                NotificationService notificationService) {
        this.retroSprintCommentGroupPersistencePort = retroSprintCommentGroupPersistencePort;
        this.retroSprintCommentPersistencePort = retroSprintCommentPersistencePort;
        this.retroSprintCommentVotePersistencePort = retroSprintCommentVotePersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.retroSprintService = retroSprintService;
        this.notificationService = notificationService;
    }


    @Transactional
    @Override
    public Optional<RetroSprintCommentVote> createRetroSprintCommentVote(String userId, String commentId, String groupId, String retroSprintId) {

        RetroSprintCommentVote newVote = new RetroSprintCommentVote();

        User user = getUser(userId);
        RetroSprintComment comment = getRetroSprintComment(commentId);
        RetroSprintCommentGroup group = getRetroSprintCommentGroup(groupId);

        newVote.setRetroSprintCommentVoteId(idGererator.generateUniqueId());
        newVote.setUser(user);
        newVote.setCreationDate(new Date());
        newVote.setRetroSprintComment(new RetroSprintComment(comment.getId()));

        group.getRetroSprintCommentSet().stream().forEach( com -> {
            if (com.getCommentType().equals(RetroSprintCommentType.ACTION) &&
                    com.getRetroSprintCommentVoteSet() != null &&
                    !com.getRetroSprintCommentVoteSet().isEmpty()) {
                com.getRetroSprintCommentVoteSet().stream().forEach(vote -> {
                    if (userId.equals(vote.getUser().getUserId())) {
                        deleteVoteByIdVote(vote.getRetroSprintCommentVoteId());
                    }
                });
            }
        });


        Optional<RetroSprintCommentVote> retroSprintCommentVote = retroSprintCommentVotePersistencePort.createRetroSprintCommentVote(newVote);
        RetroSprintComment retroSprintComment = new RetroSprintComment(commentId);
        retroSprintComment.setRetroSprintCommentGroup(new RetroSprintCommentGroup(groupId));
        retroSprintCommentVote.get().setRetroSprintComment(retroSprintComment);

        sendRetroSprintCommentVoteAddedNotification(retroSprintId, retroSprintCommentVote.get(), userId);
        return retroSprintCommentVote;
    }


    private RetroSprintComment getRetroSprintComment(String commentId) {
        Optional<RetroSprintComment> comment = retroSprintCommentPersistencePort.findByRetroSprintCommentId(commentId);
        if (!comment.isPresent()) {
            throw new RetroSprintCommentException();
        }
        return comment.get();
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


    @Transactional
    @Override
    public Optional<RetroSprintCommentVote> deleteRetroSprintCommentVote(String retroSprintCommentVoteId,
                                                                         String retroSprintCommentId,
                                                                         String retroSprintCommentGroupId,
                                                                         String retroSprintId) {

        String connectedUserId = userPersistencePort.getConnectedUserId();
        RetroSprintCommentVote vote = getRetroSprintCommentVote(retroSprintCommentVoteId);

        if (!vote.getUser().getUserId().equals(connectedUserId)) {
            throw new RetroSprintCommentVoteException("You are not allowed to delete this");
        }

        Optional<RetroSprintCommentVote> deletedVote = Optional.empty();
        if (deleteVoteByIdVote(retroSprintCommentVoteId)) {
            RetroSprintComment retroSprintComment = new RetroSprintComment(retroSprintCommentId);
            retroSprintComment.setRetroSprintCommentGroup(new RetroSprintCommentGroup(retroSprintCommentGroupId));
            vote.setRetroSprintComment(retroSprintComment);
            deletedVote = Optional.of(vote);
        }

        sendRetroSprintCommentVoteDeletedNotification(retroSprintId, deletedVote.get(), connectedUserId);
        return deletedVote;
    }

    private boolean deleteVoteByIdVote(String voteId) {
        return retroSprintCommentVotePersistencePort.deleteVoteByRetroSprintCommentVoteId(voteId);
    }

    private RetroSprintCommentVote getRetroSprintCommentVote(String voteId) {
        Optional<RetroSprintCommentVote> vote = retroSprintCommentVotePersistencePort.findVoteByRetroSprintCommentVoteId(voteId);
        if (!vote.isPresent()) {
            throw new RetroSprintCommentVoteException();
        }
        return vote.get();
    }

    private void sendRetroSprintCommentVoteAddedNotification(String retroSprintId,
                                                         RetroSprintCommentVote vote,
                                                         String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentVoteAddedNotificationToUser(invitedUserId, vote);
            }
        });
    }

    private void sendRetroSprintCommentVoteDeletedNotification(String retroSprintId,
                                                             RetroSprintCommentVote vote,
                                                             String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentVoteDeletedNotificationToUser(invitedUserId, vote);
            }
        });
    }
}
