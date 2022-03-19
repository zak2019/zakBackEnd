package com.zak.infrastructure.rest.controller.resource.dto;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityDto {
    private String responseMessage;
    private String status;
}
