package com.zak.infrastructure.rest.controller.auth.payload;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestEntity {

    private String email;
    private String password;
}
