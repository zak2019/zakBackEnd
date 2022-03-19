package com.zak.application.service.adapter;

import com.zak.application.service.api.AccountService;
import com.zak.application.service.api.NotificationService;
import com.zak.application.service.api.TeamService;
import com.zak.application.service.api.UserService;
import com.zak.domain.enums.NotificationTypeEnum;
import com.zak.domain.exception.AccountException;
import com.zak.domain.exception.TeamException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.spi.TeamPersistencePort;
import com.zak.domain.spi.IdGererator;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TeamServiceAdapter implements TeamService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private IdGererator idGererator;

    private final TeamPersistencePort teamPersistencePort;
    private final UserService userService;
    private final AccountService accountService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Autowired
    public TeamServiceAdapter(TeamPersistencePort teamPersistencePort,
                              UserService userService,
                              AccountService accountService,
                              EmailService emailService,
                              NotificationService notificationService) {
        this.teamPersistencePort = teamPersistencePort;
        this.userService = userService;
        this.accountService = accountService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @Override
    public Set<Team> getTeamsByAccountId(String accountId) {
        Account account = accountService.getAccoutByAccountId(accountId);
        return teamPersistencePort.getTeamsByAccount(account);
    }

    @Override
    public Team getTeamByTeamId(String teamId) {
        return teamPersistencePort.findTeamByTeamId(teamId);
    }

    @Override
    public Team createTeam(String accountId, Team team) {
        Team newTeam = prepareNewTeam(accountId, team);
        return teamPersistencePort.createTeam(newTeam);
    }

    private Team prepareNewTeam(String accountId, Team team) {
        Account account =
                accountService.getAccoutByAccountId(accountId);
        if (account == null) {
            throw new AccountException("Account not found !");
        }

        if (team.getTeamId() != null) {
            throw new TeamException("Team already created !");
        }
//        Optional<User> connectedUser = userService.findUserByUserId(userService.getConnectedUserId());
//        if (!connectedUser.isPresent()) {
//            throw new UserNotFoundException("Connected user not found");
//        }
        if(team.getLinkedUsers() != null){
            Set<User> linkedUsers = new HashSet<User>();
            team.getLinkedUsers()
                    .forEach(linked -> linkedUsers.add(userService.findUserByUserId(linked.getUserId()).get()));
            team.setLinkedUsers(linkedUsers);
        }
        team.setTeamId(idGererator.generateUniqueId());
        team.setCreationDate(new Date());
        team.setAccount(account);

        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        return teamPersistencePort.updateTeam(team);
    }

    @Override
    public Team addUserToTeam(String userId, String teamId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        Team team = teamPersistencePort.findTeamByTeamId(teamId);
        if (team == null) {
            throw new TeamException();
        }
        Set<User> linkedUsers = team.getLinkedUsers();
        linkedUsers.add(user.get());
        team.setLinkedUsers(linkedUsers);

        String username = user.get().getUsername();

        Team updatedTeam = teamPersistencePort.updateTeam(team);

        createAndNotifyUser(user.get(), updatedTeam);
        notifyOneUserByEmail(team, user.get());

        return updatedTeam;
    }

    private void createAndNotifyUser(User user, Team team) {
        Notification notification = new Notification();
        notification.setUserToNotify(user);
        notification.setTeam(team);

        notificationService.sendNotificationToUser(notification, NotificationTypeEnum.USER_LINKED_TO_TEAM);
    }


    @Transactional
    @Override
    public Team linkUsersToTeam(Team team) {
        Team teamByTeamId = teamPersistencePort.findTeamByTeamId(team.getTeamId());
        Set<User> newLinkedUsers = new HashSet<User>();
        if(team.getLinkedUsers() != null && team.getLinkedUsers().size() > 0){
            Set<User> linkedUsers = new HashSet<User>();
            if(teamByTeamId.getLinkedUsers() != null && teamByTeamId.getLinkedUsers().size() > 0) {
                teamByTeamId.getLinkedUsers().forEach(v -> linkedUsers.add(v));
            }
            team.getLinkedUsers()
                    .forEach(linked -> {
                        User user = userService.findUserByUserId(linked.getUserId()).get();
                        linkedUsers.add(user);
                        newLinkedUsers.add(user);
                    });
            teamByTeamId.setLinkedUsers(linkedUsers);
        }

        Team updatedTeam = teamPersistencePort.updateTeam(teamByTeamId);
//        messagingTemplate.convertAndSend("/topic/progress", team);

//        createAndNotifyUser(user, updatedTeam);
        notifyUsers(newLinkedUsers, teamByTeamId);

        return updatedTeam;
    }

    public void notifyUsers(Set<User> users, Team team) {

        users.forEach(user -> {
            notifyOneUserByEmail(team, user);
            createAndNotifyUser(user, team);
        });

    }

    public void notifyOneUserByEmail(Team teamByTeamId, User user) {
        emailService.sendEmail(
                user.getEmail(),
                "New team",
                "<h3>You have been assigned to the team " + teamByTeamId.getTeamName() + "</h1>"
        );
    }

    @Override
    public Team addUserToNewTeam(String accountId, String userId, Team team) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        Team newTeam = prepareNewTeam(accountId, team);
        Set<User> linkedUsers = newTeam.getLinkedUsers();
        linkedUsers.add(user.get());
        newTeam.setLinkedUsers(linkedUsers);
        return teamPersistencePort.createTeam(newTeam);
    }
}
