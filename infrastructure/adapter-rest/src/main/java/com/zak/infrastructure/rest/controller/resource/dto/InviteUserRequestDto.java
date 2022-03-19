package com.zak.infrastructure.rest.controller.resource.dto;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InviteUserRequestDto {

    private String emails;
}
