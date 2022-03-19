package com.zak.application.service.adapter;

import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.RetroSprintCommentService;
import com.zak.application.service.api.RetroSprintService;
import com.zak.domain.enums.RetroSprintCommentType;
import com.zak.domain.exception.*;
import com.zak.domain.model.*;
import com.zak.domain.spi.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class RetroSprintCommentServiceAdapter implements RetroSprintCommentService {

    private final RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort;
    private final RetroSprintCommentPersistencePort retroSprintCommentPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final NotificationService notificationService;
    private final RetroSprintService retroSprintService;
    private final IdGererator idGererator;


    @Autowired
    public RetroSprintCommentServiceAdapter(RetroSprintCommentGroupPersistencePort retroSprintCommentGroupPersistencePort,
                                            RetroSprintCommentPersistencePort retroSprintCommentPersistencePort,
                                            UserPersistencePort userPersistencePort,
                                            NotificationService notificationService,
                                            RetroSprintService retroSprintService,
                                            IdGererator idGererator) {
        this.retroSprintCommentGroupPersistencePort = retroSprintCommentGroupPersistencePort;
        this.retroSprintCommentPersistencePort = retroSprintCommentPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.notificationService = notificationService;
        this.retroSprintService = retroSprintService;
        this.idGererator = idGererator;
    }


    @Override
    public Optional<RetroSprintComment> createRetroSprintComment(String retroSprintId, String userId, String retroSprintCommentGroupId, RetroSprintComment retroSprintComment) {

        Optional<User> user = userPersistencePort.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        RetroSprintCommentGroup retroSprintCommentGroup = getRetroSprintCommentGroup(retroSprintCommentGroupId);

        retroSprintComment.setRetroSprintCommentId(idGererator.generateUniqueId());
        retroSprintComment.setCreationDate(new Date());
        retroSprintComment.setRetroSprintCommentGroup(retroSprintCommentGroup);
        retroSprintComment.setUser(user.get());
        retroSprintComment.setCommentPosition(getCommentPosition(retroSprintCommentGroup, retroSprintComment));
        Optional<RetroSprintComment> savedComment = retroSprintCommentPersistencePort.createRetroSprintComment(retroSprintComment);

        sendRetroSprintCommentAddedNotification(retroSprintId, retroSprintComment, retroSprintCommentGroup, userId);
        return savedComment;
    }

    private int getCommentPosition(RetroSprintCommentGroup retroSprintCommentGroup, RetroSprintComment retroSprintComment) {
        return retroSprintCommentGroup.getRetroSprintCommentSet()
                .stream().filter(comment ->
                        comment.getCommentType() == retroSprintComment.getCommentType())
                .collect(Collectors.toSet()).size() + 1;
    }

    @Override
    public Optional<RetroSprintComment> updateRetroSprintComment(String retroSprintId, String retroSprintCommentGroupId, RetroSprintComment retroSprintComment) {
        RetroSprintCommentGroup retroSprintCommentGroup = getRetroSprintCommentGroup(retroSprintCommentGroupId);
        RetroSprintComment comment = getRetroSprintComment(retroSprintComment.getRetroSprintCommentId());

        retroSprintComment.setId(comment.getId());
        retroSprintComment.setRetroSprintCommentGroup(retroSprintCommentGroup);
        Optional<RetroSprintComment> updatedComment = retroSprintCommentPersistencePort.updateRetroSprintComment(retroSprintComment);
        if(updatedComment.isPresent()) {
            RetroSprintComment retroSprintCommentForNotification = updatedComment.get();
            retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprintId));
            retroSprintCommentForNotification.setRetroSprintCommentGroup(retroSprintCommentGroup);
            sendRetroSprintCommentUpdatedNotification(retroSprintId, retroSprintCommentForNotification, retroSprintCommentGroup);
        }
        return updatedComment;
    }

    private RetroSprintCommentGroup getRetroSprintCommentGroup(String retroSprintCommentGroupId) {
        Optional<RetroSprintCommentGroup> retroSprintCommentGroup =
                retroSprintCommentGroupPersistencePort.findByRetroSprintCommentGroupId(retroSprintCommentGroupId);
        if (!retroSprintCommentGroup.isPresent()) {
            throw new RetroSprintCommentGroupException();
        }
        return retroSprintCommentGroup.get();
    }

    @Override
    public Set<RetroSprintComment> deleteRetroSprintCommentByRetroSprintCommentIdAndGroupId(String retroSprintCommentId, String retroSprintCommentGroupId, String retroSprintId) {

        String connectedUserId = userPersistencePort.getConnectedUserId();
        RetroSprintComment retroSprintCommentToDelete = getRetroSprintComment(retroSprintCommentId);

        if (!connectedUserId.equals(retroSprintCommentToDelete.getUser().getUserId())) {
            throw new RetroSprintCommentException("You can't delete this comment");
        }

        RetroSprintCommentGroup retroSprintCommentGroup = getRetroSprintCommentGroup(retroSprintCommentGroupId);

        Set<RetroSprintComment> updatedComments = new HashSet<>();
        retroSprintCommentToDelete.getRetroSprintCommentVoteSet()
                .stream().forEach(vote -> vote.setRetroSprintComment(new RetroSprintComment(retroSprintCommentToDelete.getId())));
        if(retroSprintCommentPersistencePort.deleteRetroSprintComment(retroSprintCommentToDelete)) {
            managePreviousRetroSprintCommentPosition(retroSprintCommentToDelete, retroSprintCommentGroup, updatedComments, retroSprintId);
        }

        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprintId));
        retroSprintCommentToDelete.setRetroSprintCommentGroup(retroSprintCommentGroup);
        // set deleted comment position to 0, to deleted in the UI
        retroSprintCommentToDelete.setCommentPosition(0);
        updatedComments.add(retroSprintCommentToDelete);

        // send updates to connected users
        sendRetroSprintCommentDeletedNotification(retroSprintId, updatedComments, connectedUserId);

        // return updated position comments and deleted comment to update data in UI
        return updatedComments;
    }

    private RetroSprintComment getRetroSprintComment(String retroSprintCommentId) {
        Optional<RetroSprintComment> retroSprintComment =
                retroSprintCommentPersistencePort.findByRetroSprintCommentId(retroSprintCommentId);
        if (!retroSprintComment.isPresent()) {
            throw new RetroSprintCommentException("Retro sprint comment not found");
        }
        return retroSprintComment.get();
    }

    @Override
    public Set<RetroSprintComment> getCommentsByRetroSprintCommentGroupId(String retroSprintCommentGroupId) {

        RetroSprintCommentGroup group = getRetroSprintCommentGroup(retroSprintCommentGroupId);

        return retroSprintCommentPersistencePort.findByRetroSprintCommentGroup(group);
    }

    @Override
    public DragDropRetroSprintComment changeRetroSprintCommentPosition(String retroSprintCommentId,
                                                                    String retroSprintCommentGroupId,
                                                                    String retroSprintId,
                                                                    int toPosition) {

        RetroSprintComment retroSprintComment = checkRetroSprintComment(retroSprintCommentId);
        RetroSprintCommentGroup retroSprintCommentGroup = checkRetroSprintCommentGroup(retroSprintCommentGroupId);

        int commentPosition = retroSprintComment.getCommentPosition();
        Set<RetroSprintComment> allComment = new HashSet<>();
        Set<RetroSprintComment> updatedComments = new HashSet<>();
        if (toPosition > commentPosition) {
            allComment = getBySprintRetroGroupAndByCommentTypeAndByPosition(
                    retroSprintCommentGroup,
                    retroSprintComment.getCommentType(),
                    commentPosition,
                    toPosition);
            decrementCommentPositions(toPosition, retroSprintId, retroSprintCommentGroup, retroSprintComment, allComment, updatedComments);
        } else {
            allComment = getBySprintRetroGroupAndByCommentTypeAndByPosition(
                    retroSprintCommentGroup,
                    retroSprintComment.getCommentType(),
                    toPosition,
                    commentPosition);
            incrementCommentPositions(toPosition, retroSprintId, retroSprintCommentGroup, retroSprintComment, allComment, updatedComments);

        }
        updatePositionAndSave(retroSprintCommentGroup, updatedComments, retroSprintComment, toPosition, retroSprintId);

        DragDropRetroSprintComment dragDropRetroSprintComment = new DragDropRetroSprintComment(retroSprintCommentId, updatedComments);
        sendRetroSprintCommentDragDroppedInSameGroupNotification(retroSprintId, dragDropRetroSprintComment);
        return dragDropRetroSprintComment;
    }

    private void incrementCommentPositions(int toPosition, String retroSprintId, RetroSprintCommentGroup retroSprintCommentGroup, RetroSprintComment retroSprintComment, Set<RetroSprintComment> allComment, Set<RetroSprintComment> updatedComments) {
        allComment.stream().forEach(comment -> {
            if (comment.getRetroSprintCommentId() != retroSprintComment.getRetroSprintCommentId() &&
                    comment.getCommentPosition() < retroSprintComment.getCommentPosition() && comment.getCommentPosition() >= toPosition) {
                updatePositionAndSave(retroSprintCommentGroup, updatedComments, comment, comment.getCommentPosition() + 1, retroSprintId);
            }
        });
    }

    private void decrementCommentPositions(int toPosition, String retroSprintId, RetroSprintCommentGroup retroSprintCommentGroup,
                                           RetroSprintComment retroSprintComment, Set<RetroSprintComment> allComment,
                                           Set<RetroSprintComment> updatedComments) {
        allComment.stream().forEach(comment -> {
            if (comment.getRetroSprintCommentId() != retroSprintComment.getRetroSprintCommentId() &&
                    comment.getCommentPosition() > retroSprintComment.getCommentPosition() && comment.getCommentPosition() <= toPosition) {
                updatePositionAndSave(retroSprintCommentGroup, updatedComments, comment, comment.getCommentPosition() - 1, retroSprintId);
            }
        });
    }

    private void updatePositionAndSave(RetroSprintCommentGroup retroSprintCommentGroup,
                                       Set<RetroSprintComment> updatedComments,
                                       RetroSprintComment comment, int i,
                                       String retroSprintId) {
        comment.setCommentPosition(i);
        comment.setRetroSprintCommentGroup(new RetroSprintCommentGroup(retroSprintCommentGroup.getId()));
        RetroSprintComment savedComment = retroSprintCommentPersistencePort.updateRetroSprintComment(comment).get();
        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprintId));
        savedComment.setRetroSprintCommentGroup(retroSprintCommentGroup);
        updatedComments.add(savedComment);
    }

    @Override
    public DragDropRetroSprintComment changeRetroSprintCommentPositionAndGroup(String retroSprintId,
                                                                            String retroSprintCommentId,
                                                                            String fromGroupId,
                                                                            String toGroupId,
                                                                            int toPosition) {
        RetroSprintComment retroSprintComment = checkRetroSprintComment(retroSprintCommentId);
        RetroSprintCommentGroup previousRetroSprintCommentGroup = checkRetroSprintCommentGroup(fromGroupId);
        RetroSprintCommentGroup destinationRetroSprintCommentGroup = checkRetroSprintCommentGroup(toGroupId);

        Set<RetroSprintComment> updatedComments = new HashSet<>();

        managePreviousRetroSprintCommentPosition(retroSprintComment, previousRetroSprintCommentGroup, updatedComments, retroSprintId);

        Set<RetroSprintComment> destinationGroupCommentList = getBySprintRetroGroupAndByCommentTypeStartingFromPosition(
                destinationRetroSprintCommentGroup,
                retroSprintComment.getCommentType(),
                toPosition);

        destinationGroupCommentList.stream().forEach(comment -> {
            if (comment.getCommentPosition() >= toPosition) {
                updatePositionAndSave(destinationRetroSprintCommentGroup, updatedComments, comment, comment.getCommentPosition() + 1, retroSprintId);
            }
        });

        retroSprintComment.setRetroSprintCommentGroup(destinationRetroSprintCommentGroup);
        updatePositionAndSave(destinationRetroSprintCommentGroup, updatedComments, retroSprintComment, toPosition, retroSprintId);

        DragDropRetroSprintComment dragDropRetroSprintComment = new DragDropRetroSprintComment(retroSprintCommentId, updatedComments, fromGroupId, toGroupId);
        sendRetroSprintCommentDragDroppedInOtherGroupNotification(retroSprintId, dragDropRetroSprintComment);
        return dragDropRetroSprintComment;
    }

    private void managePreviousRetroSprintCommentPosition(RetroSprintComment retroSprintComment,
                                                          RetroSprintCommentGroup previousRetroSprintCommentGroup,
                                                          Set<RetroSprintComment> updatedComments,
                                                          String retroSprintId) {
        Set<RetroSprintComment> previousGroupCommentList = getBySprintRetroGroupAndByCommentTypeStartingFromPosition(
                previousRetroSprintCommentGroup,
                retroSprintComment.getCommentType(),
                retroSprintComment.getCommentPosition());

        previousGroupCommentList.stream().forEach(comment -> {
            if (comment.getRetroSprintCommentId() != retroSprintComment.getRetroSprintCommentId() &&
                    comment.getCommentPosition() > retroSprintComment.getCommentPosition()) {
                updatePositionAndSave(previousRetroSprintCommentGroup, updatedComments, comment, comment.getCommentPosition() - 1, retroSprintId);
            }
        });
    }

    private RetroSprintCommentGroup checkRetroSprintCommentGroup(String retroSprintCommentGroupId) {
        Optional<RetroSprintCommentGroup> retroSprintCommentGroup =
                retroSprintCommentGroupPersistencePort.findByRetroSprintCommentGroupId(retroSprintCommentGroupId);
        if (!retroSprintCommentGroup.isPresent()) {
            throw new RetroSprintCommentGroupException();
        }
        return retroSprintCommentGroup.get();
    }

    private RetroSprintComment checkRetroSprintComment(String retroSprintCommentId) {
        Optional<RetroSprintComment> com = retroSprintCommentPersistencePort.findByRetroSprintCommentId(retroSprintCommentId);
        if (!com.isPresent()) {
            throw new RetroSprintCommentException();
        }
        return com.get();
    }

    private Set<RetroSprintComment> getBySprintRetroGroupAndByCommentTypeAndByPosition(RetroSprintCommentGroup retroSprintCommentGroup,
                                                                                       RetroSprintCommentType retroSprintCommentType,
                                                                                       int fistPosition,
                                                                                       int secondPosition) {
        return retroSprintCommentPersistencePort
                .findBySprintRetroCommentGroupAndByCommentTypeAndByPositions(
                        retroSprintCommentGroup,
                        retroSprintCommentType,
                        fistPosition,
                        secondPosition);
    }

    private Set<RetroSprintComment> getBySprintRetroGroupAndByCommentTypeStartingFromPosition(
            RetroSprintCommentGroup retroSprintCommentGroup,
            RetroSprintCommentType type,
            int position) {
        return retroSprintCommentPersistencePort
                .findBySprintRetroGroupAndByCommentTypeStartingFromPosition(
                        retroSprintCommentGroup,
                        type,
                        position);
    }

    private void sendRetroSprintCommentUpdatedNotification(String retroSprintId,
                                                         RetroSprintComment comment,
                                                         RetroSprintCommentGroup retroSprintCommentGroup) {
        String connectedUserId = userPersistencePort.getConnectedUserId();
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprintId));
        comment.setRetroSprintCommentGroup(retroSprintCommentGroup);

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentUpdatedNotificationToUser(invitedUserId, comment);
            }
        });
    }

    private void sendRetroSprintCommentAddedNotification(String retroSprintId,
                                                                  RetroSprintComment comment,
                                                         RetroSprintCommentGroup retroSprintCommentGroup,
                                                                  String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        retroSprintCommentGroup.setRetroSprint(new RetroSprint(retroSprintId));
        comment.setRetroSprintCommentGroup(retroSprintCommentGroup);

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentAddedNotificationToUser(invitedUserId, comment);
            }
        });
    }

    private void sendRetroSprintCommentDeletedNotification(String retroSprintId,
                                                         Set<RetroSprintComment> updatedComments,
                                                         String connectedUserId) {
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentDeletedNotificationToUser(invitedUserId, updatedComments);
            }
        });
    }

    private void sendRetroSprintCommentDragDroppedInSameGroupNotification(String retroSprintId,
                                                           DragDropRetroSprintComment dragDropRetroSprintComment) {
        String connectedUserId = userPersistencePort.getConnectedUserId();
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentDragDroppedInSameGroupNotificationToUser(invitedUserId, dragDropRetroSprintComment);
            }
        });
    }

    private void sendRetroSprintCommentDragDroppedInOtherGroupNotification(String retroSprintId,
                                                                          DragDropRetroSprintComment dragDropRetroSprintComment) {
        String connectedUserId = userPersistencePort.getConnectedUserId();
        RetroSprint retroSprint = retroSprintService.getRetroSprintByRetroSprintId(retroSprintId);
        Set<User> invitedUsers = retroSprint.getEventRetro().getUsers();

        invitedUsers.forEach(invitedUser -> {
            String invitedUserId = invitedUser.getUserId();
            if (!invitedUserId.equals(connectedUserId)) {
                notificationService.sendRetroSprintCommentDragDroppedInOtherGroupNotificationToUser(invitedUserId, dragDropRetroSprintComment);
            }
        });
    }
}
