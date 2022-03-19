package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.UserService;
import com.zak.domain.model.User;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.InviteUserRequestDto;
import com.zak.infrastructure.rest.controller.resource.dto.UserDto;
import com.zak.infrastructure.rest.controller.resource.dto.UsersUsersAssociationDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/user")
public class UserControllerImpl {

    private final UserService userService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public UserControllerImpl(UserService userService, MapperFacade orikaMapperFacade) {
        this.userService = userService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/username/{username}")
        // @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    Optional<UserDto> findByUsername(@PathVariable String username) {
        return Optional.of(
                orikaMapperFacade.map(userService.findByUsername(username).get(),
                        UserDto.class)
        );
    }

    @GetMapping("/userId/{userId}")
    Optional<UserDto> findByUserId(@PathVariable String userId) {
        return Optional.of(
                orikaMapperFacade.map(userService.findUserByUserId(userId).get(),
                        UserDto.class)
        );
    }

    @PostMapping("/update")
    public ResponseContent<?> updateUserInfo(@RequestBody final User user) {
        Optional<User> updatedUser = userService.updateUser(user);
        return new ResponseContent<UserDto>(
                orikaMapperFacade.map(updatedUser.get(), UserDto.class), "User updated successfully");
    }

    @PostMapping("/complete-account/{associationId}")
    public ResponseEntity<?> completeUserAccount(@PathVariable final String associationId,
                                                 @RequestBody final User user) {
        Optional<User> savedUser = userService.completeUserAccountCreation(
                associationId,
                user
                );
        return ResponseEntity.ok(new MessageResponseEntity("Account completed successfully!"));
    }

    @PostMapping("/invite-users/{accountId}")
    public ResponseContent<UsersUsersAssociationDto> inviteUser(@RequestBody final InviteUserRequestDto usersEmail,
                                                                @PathVariable final String accountId) {
        List<ResponseContent<UsersUsersAssociation>> associations = userService.inviteUsers(usersEmail.getEmails(), accountId);

        List<UsersUsersAssociationDto> assocBody = new ArrayList<UsersUsersAssociationDto>();
        List<String> respMessages = new ArrayList<String>();
        associations.forEach(assoc -> {
            assocBody.add(orikaMapperFacade.map(assoc.getBody(), UsersUsersAssociationDto.class));
            respMessages.add(assoc.getMessage());
        });

       return new ResponseContent(assocBody, respMessages);
    }

    @PostMapping("/add-role-to-user/{idUser}/{role}")
    public ResponseEntity<?> addRoleToUser(@PathVariable final String idUser,
                                           @PathVariable final String role) {
        userService.addRoleToUser(idUser, role);
        return ResponseEntity.ok(new MessageResponseEntity("Role added successfully"));
    }

    @PostMapping("/delete-role-from-user/{idUser}/{role}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable final String idUser,
                                                @PathVariable final String role) {
        userService.deleteRoleFromUser(idUser, role);
        return ResponseEntity.ok(new MessageResponseEntity("Role deleted successfully"));
    }

    @GetMapping("/exist/username/{username}")
    Boolean existsByUsername(@PathVariable String username) {
        return userService.existsByUsername(username);
    }

    @GetMapping("/exist/email/{email}")
    Boolean existsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email);
    }
}
