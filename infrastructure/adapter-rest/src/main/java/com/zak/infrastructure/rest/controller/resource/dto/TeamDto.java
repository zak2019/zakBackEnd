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
public class TeamDto {

    private String teamId;
    private String teamName;
    private Date creationDate;
    private AccountDto account;
    private Set<UserDto> linkedUsers = new HashSet<UserDto>();
}
