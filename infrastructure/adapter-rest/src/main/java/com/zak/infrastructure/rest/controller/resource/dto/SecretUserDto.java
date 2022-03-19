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
public class SecretUserDto {

    private String SecretId;
    private boolean isEnabled;
}
