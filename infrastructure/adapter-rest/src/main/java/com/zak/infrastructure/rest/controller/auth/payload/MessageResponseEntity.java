package com.zak.infrastructure.rest.controller.auth.payload;

import lombok.*;

@Builder
@Setter
@Getter
public class MessageResponseEntity {

    private String message;

    public MessageResponseEntity(){}

    public MessageResponseEntity(String message) {
        this.message = message;
    }
}
