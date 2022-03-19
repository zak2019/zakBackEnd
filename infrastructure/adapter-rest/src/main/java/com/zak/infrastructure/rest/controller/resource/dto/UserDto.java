package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String linkedInId;
    private String roleInProject;
    private String email;
    private Set<UserRoleDto> roles = new HashSet<UserRoleDto>();
    private Set<UsersUsersAssociationDto> inviter = new HashSet<UsersUsersAssociationDto>();
    private Set<UsersUsersAssociationDto> invitedUsers = new HashSet<UsersUsersAssociationDto>();
    private boolean isEnabled;
    private Date creationDate;
}
