package com.zak.infrastructure.rest.controller.auth;

import java.util.*;
import java.util.stream.Collectors;

import com.zak.application.service.api.*;
import com.zak.domain.model.*;
import com.zak.infrastructure.rest.controller.auth.payload.JwtResponseEntity;
import com.zak.infrastructure.rest.controller.auth.payload.LoginRequestEntity;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.auth.payload.SignupRequestEntity;
import com.zak.persistence.jpa.security.JwtUtils;
import com.zak.persistence.jpa.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UsersUsersAssociationService usersAssociationService;

    @Autowired
    AccountService accountService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    UserRoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestEntity loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponseEntity(jwt,
                userDetails.getUserId(),
                userDetails.getSecretId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestEntity signUpRequest) {
        Account accountData = signUpRequest.getAccount();
        User userData = signUpRequest.getUser();
        if (userService.existsByUsername(userData.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseEntity("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(userData.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseEntity("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(userData.getUsername(),
                userData.getEmail(),
                encoder.encode(userData.getPassword()),
                new Date());
//
//        Set<String> strRoles = userData.getRole();
//        Set<UserRole> roles = new HashSet<UserRole>();
//
//        if (strRoles == null) {
//            UserRole adminRole = roleService.findByName(EUserRole.ROLE_ADMIN)
//                    .orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
//            roles.add(adminRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        UserRole userRoleAdmin = roleService.findByName(EUserRole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
//                        roles.add(userRoleAdmin);
//
//                        break;
//                    case "mod":
//                        UserRole userRoleMod = roleService.findByName(EUserRole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role moderator is not found."));
//                        roles.add(userRoleMod);
//
//                        break;
//                    default:
//                        UserRole userRole = roleService.findByName(EUserRole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Default role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userService.createUser(user);
        accountService.createAccount(accountData, user);

        return ResponseEntity.ok(new MessageResponseEntity("Account created successfully!"));
    }

    @GetMapping("/account/{associationId}/confirm")
    public ResponseEntity<?> confirmUserAccount(@PathVariable String associationId,
                                                @RequestParam("t") String confirmationToken) {
        Optional<ConfirmationToken> token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        UsersUsersAssociation association = usersAssociationService.findByAssociationId(associationId);

        if(association == null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseEntity("Invalid link"));
        }

        ConfirmationToken currentToken = token.get();
        Date date = new Date();
        Date tokenCreationDate = currentToken.getCreatedDate();
        if (date.getTime() - tokenCreationDate.getTime() > 86400000) {
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(new MessageResponseEntity("The link has expired!"));
        }

        try {
            association.setEnabled(true);
            usersAssociationService.updateAssociation(association);
            User user = userService.findUserByEmail(currentToken.getUser().getEmail()).get();
            user.setEnabled(true);
            userService.updateUser(user);
            confirmationTokenService.deleteConfirmationToken(currentToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseEntity("Error"));
        }
        return ResponseEntity.ok(new MessageResponseEntity("Email address confirmed successfully!"));
    }
}
