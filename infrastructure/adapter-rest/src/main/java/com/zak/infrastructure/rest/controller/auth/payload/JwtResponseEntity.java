package com.zak.infrastructure.rest.controller.auth.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class JwtResponseEntity {
    private String accessToken;
    private String userId;
    private String secretId;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<String>();

    public JwtResponseEntity(String accessToken, String userId, String secretId, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.secretId = secretId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
