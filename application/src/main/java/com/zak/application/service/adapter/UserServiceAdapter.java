package com.zak.application.service.adapter;

import com.zak.application.service.api.*;
import com.zak.domain.enums.EUserRole;
import com.zak.domain.exception.AccountException;
import com.zak.domain.exception.UserNotFoundException;
import com.zak.domain.model.*;
import com.zak.domain.model.util.ResponseContent;
import com.zak.domain.spi.AccountPersistencePort;
import com.zak.domain.spi.IdGererator;
import com.zak.domain.spi.UserPersistencePort;
import com.zak.domain.spi.UserRolePersistencePort;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.security.AccessControlException;
import java.util.*;
import java.util.stream.Collectors;

public class UserServiceAdapter implements UserService {

    private final UserPersistencePort userPersistencePort;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRoleService userRoleService;
    private final UserRolePersistencePort userRolePersistencePort;
    private final UsersUsersAssociationService usersUsersAssociationService;
    private final AccountPersistencePort accountPersistencePort;

    @Autowired
    private IdGererator idGererator;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserServiceAdapter(UserPersistencePort userPersistencePort,
                              ConfirmationTokenService confirmationTokenService,
                              EmailService emailService,
                              UserRoleService userRoleService,
                              UserRolePersistencePort userRolePersistencePort,
                              UsersUsersAssociationService usersUsersAssociationService,
                              AccountPersistencePort accountPersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.userRoleService = userRoleService;
        this.userRolePersistencePort = userRolePersistencePort;
        this.usersUsersAssociationService = usersUsersAssociationService;
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user) {
        Optional<User> createdUser = persistNewUser(user);

        if (createdUser.isPresent()) {
            Optional<ConfirmationToken> confirmationToken =
                    confirmationTokenService.createAndAddConfirmationToken(createdUser.get());

            emailService.sendEmail(
                    createdUser.get().getEmail(),
                    "Complete Registration!",
                    "<h3>Welcome To ZAK " + createdUser.get().getUsername() + "</h1><p>To confirm your account, please click here : " +
                            "http://localhost:8080/api/auth/confirm-account?token=" + confirmationToken.get().getConfirmationToken() + "</p>"
            );
        }

        return createdUser;
    }

    @Override
    public Optional<User> persistNewUser(User user) {
        user.setUserId(idGererator.generateUniqueId());
        user.setSecretId(idGererator.generateUniqueId());
        return userPersistencePort.createUser(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        return userPersistencePort.updateUser(user);
    }

    @Override
    public Optional<User> completeUserAccountCreation(String associationId, User user) {

        UsersUsersAssociation usersAssociation = usersUsersAssociationService.findByAssociationId(associationId);
        User inviter = usersAssociation.getInviter();
        User invitedUser = usersAssociation.getInvitedUser();

        if (usersAssociation == null || invitedUser == null || inviter == null) {
            throw new AccessControlException("Invalid link, User account not found");
        }

        if (usersAssociation.isEnabled() || invitedUser.isEnabled()) {
            throw new AccessControlException("Invalid link, User account already activated");
        }

        invitedUser.setUsername(user.getUsername());
        invitedUser.setEnabled(true);
        invitedUser.setPassword(encoder.encode(user.getPassword()));
        Optional<User> savedInvitedUser = userPersistencePort.updateUser(invitedUser);

        usersAssociation.setEnabled(true);
        UsersUsersAssociation usersUsersAssociation =
                usersUsersAssociationService.updateAssociation(usersAssociation);

        if (savedInvitedUser.isPresent() && savedInvitedUser.get().isEnabled() &&
                usersUsersAssociation != null && usersUsersAssociation.isEnabled()) {
            emailService.sendEmail(
                    savedInvitedUser.get().getEmail(),
                    "Registration completed!",
                    "<h3>Welcome To ZAK " + savedInvitedUser.get().getUsername() + "</h1>" +
                            "<p>Your account has been completed and activated successfully," +
                            " Now you have access to all our services on http://localhost:4200/home</p>"
            );
        }
        return savedInvitedUser;
    }

    @Override
    public Optional<User> addRoleToUser(String idUser, String role) {
        Optional<User> oldData = userPersistencePort.findUserByUserId(idUser);
        if (!oldData.isPresent()) {
            throw new UserNotFoundException();
        }
        UserRole newRole = userRoleService.getRole(role);
        Set<UserRole> userRoles = oldData.get().getRoles();
        if (!userRoles.contains(newRole)) {
            userRoles.add(newRole);
            oldData.get().setRoles(userRoles);
        }

        return userPersistencePort.updateUser(oldData.get());
    }

    @Override
    public Optional<User> deleteRoleFromUser(String idUser, String role) {
        Optional<User> oldData = userPersistencePort.findUserByUserId(idUser);
        if (!oldData.isPresent()) {
            throw new UserNotFoundException();
        }
        UserRole roleToDelete = userRoleService.getRole(role);
        Set<UserRole> userRoles = oldData.get().getRoles();
        if (userRoles.contains(roleToDelete)) {
            userRoles.remove(roleToDelete);
            oldData.get().setRoles(userRoles);
        }

        return userPersistencePort.updateUser(oldData.get());
    }

    @Override
    public List<ResponseContent<UsersUsersAssociation>> inviteUsers(String emails, String accountId) {
        List<ResponseContent<UsersUsersAssociation>> associations = new ArrayList<ResponseContent<UsersUsersAssociation>>();

        String[] emailsList = transformEmailsToEmailsList(emails);

        Arrays.stream(emailsList).forEach(email -> {
            ResponseContent<UsersUsersAssociation> usersUsersAssociation = inviteOneUser(email, accountId);
            if (usersUsersAssociation != null) {
                associations.add(usersUsersAssociation);
            }
        });

        return associations;
    }

    private String[] transformEmailsToEmailsList(String emails) {
        String emailsWithoutSpaces = emails.replaceAll("\\s+", "");
        return emailsWithoutSpaces.split(";");
    }


    @Override
    public String getInviteUsersResponseMessage(List<UsersUsersAssociation> associations, String emails) {
        String[] emailsList = transformEmailsToEmailsList(emails);
        String responseReport = null;
        if (associations.size() == emailsList.length && emailsList.length > 1) {
            responseReport =
                    "All invitations(" + emailsList.length + "/" + emailsList.length + ") are send successfully!";
        } else if (associations.size() == emailsList.length && emailsList.length == 1) {
            responseReport = "Invitation send successfully!";
        } else if (emailsList.length == 1 && associations.size() < emailsList.length) {
            responseReport = "Email invalid";
        } else {
            int valid = 0;
            int inValid = 0;
            String inValidAdresses = "";
            for (String email : emailsList) {
                List<UsersUsersAssociation> associationsList = associations.stream().
                        filter(association -> association.getInvitedUser().getEmail().equals(email))
                        .collect(Collectors.toList());
                if (associationsList.size() > 0) {
                    valid++;
                } else {
                    inValid++;
                    inValidAdresses += email + "; ";
                }
            }
            responseReport = valid + "/" +
                    emailsList.length + " invitations are send successfully!, invalid Email syntax: " + inValidAdresses;
        }

        return responseReport;
    }

    private ResponseContent<UsersUsersAssociation> inviteOneUser(String email, String accountId) {
        if (!isEmailValid(email)) {
            return new ResponseContent<UsersUsersAssociation>(null,
                    "Invalid email address : " + email);
        }
        String connectedUserID = getConnectedUserId();
        Assert.notNull(connectedUserID, "Error with connected user");
        Optional<User> connectedUser = findUserByUserId(connectedUserID);
        if (!connectedUser.isPresent()) {
            throw new UserNotFoundException("Connected User not found");
        }

        Account account = accountPersistencePort.getAccount(accountId);
        if (account == null) {
            throw new AccountException("Account not found");
        }

        UserRole userRole = userRoleService.findByName(EUserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: role USER not found."));
        Set<UserRole> roles = new HashSet<UserRole>();
        roles.add(userRole);

        UsersUsersAssociation usersUsersAssociation;
        if(existsByEmail(email)) {
            Optional<User> newUser = findUserByEmail(email);
            if(usersUsersAssociationService
                    .findByInvitedUserAndInviterAndAccount(newUser.get(), connectedUser.get(), account) != null) {
               return new ResponseContent<UsersUsersAssociation>(null,
                        email + " : is already in your list ");
            } else {
                usersUsersAssociation = createAssociationWithExistingUser(connectedUser, roles, newUser, account);
            }
        } else {
            usersUsersAssociation = createAssociationWithNewUser(email, connectedUser, roles, account);
        }

        return new ResponseContent<UsersUsersAssociation>(usersUsersAssociation, email + " : Invitation send successfully");
    }

    private UsersUsersAssociation createAssociationWithNewUser(String email,
                                                               Optional<User> connectedUser,
                                                               Set<UserRole> roles,
                                                               Account account) {
        Optional<User> newUser = saveNewUser(email);
        UsersUsersAssociation usersUsersAssociation =
                usersUsersAssociationService.saveUsersAssociation(connectedUser.get(), newUser.get(), roles, account);
        if (newUser.isPresent() && usersUsersAssociation!= null) {

            emailService.sendEmail(
                    newUser.get().getEmail(),
                    "Complete Account!",
                    "<h3>Welcome To ZAK </h1><p>To complete and confirm your account," +
                            "please click or copy  this link : " +
                            "http://localhost:4200/complete-registration/" + usersUsersAssociation.getAssociationId() + "</p>"
            );
        }
        return usersUsersAssociation;
    }

    private UsersUsersAssociation createAssociationWithExistingUser(Optional<User> connectedUser,
                                                                    Set<UserRole> roles,
                                                                    Optional<User> newUser,
                                                                    Account account) {

        UsersUsersAssociation usersUsersAssociation =
                usersUsersAssociationService.saveUsersAssociation(connectedUser.get(), newUser.get(), roles, account);
       if(usersUsersAssociation != null) {
           emailService.sendEmail(
                   newUser.get().getEmail(),
                   "Complete Association!",
                   "<h3>Welcome To ZAK </h1><p>You've been invited by <b>"
                           + usersUsersAssociation.getInviter().getUsername()+
                           "</b> to join the <b>"
                           + usersUsersAssociation.getAccount().getAccountName()+
                           "</b> project, please click or copy the link to complete your registration: <br>" +
                           "http://localhost:4200/complete-registration/" + usersUsersAssociation.getAssociationId() +"</p>"
           );
       }
        return usersUsersAssociation;
    }

    private Optional<User> saveNewUser(String email) {
        User invitedUser = new User();
        invitedUser.setUserId(idGererator.generateUniqueId());
        invitedUser.setSecretId(idGererator.generateUniqueId());
        invitedUser.setEmail(email);
        invitedUser.setUsername(email);
        invitedUser.setCreationDate(new Date());
        return userPersistencePort.createUser(invitedUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userPersistencePort.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        getConnectedUserId();
        return userPersistencePort.findUserByUserId(userId);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userPersistencePort.findUserByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userPersistencePort.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userPersistencePort.existsByEmail(email);
    }

    @Override
    public String getConnectedUserId() {
        return userPersistencePort.getConnectedUserId();
    }

    private boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
