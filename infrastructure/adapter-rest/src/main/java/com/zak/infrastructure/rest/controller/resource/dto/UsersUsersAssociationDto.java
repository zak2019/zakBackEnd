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
public class UsersUsersAssociationDto {

    private String associationId;

    private UserDto invitedUser;

    private UserDto inviter;

    private AccountDto account;

    private Date creationDate;

    private boolean isEnabled;

    private Set<UserRoleDto> roles = new HashSet<UserRoleDto>();
}
