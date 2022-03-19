package com.zak.infrastructure.rest.controller.resource.dto;

import com.zak.domain.enums.EUserRole;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

    private Integer id;

    private EUserRole name;

    private String label;
}
