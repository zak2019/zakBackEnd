package com.zak.infrastructure.rest.controller.resource.dto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String accountId;

    private String accountName;

    private Date creationDate;

    private boolean isEnabled;
}
