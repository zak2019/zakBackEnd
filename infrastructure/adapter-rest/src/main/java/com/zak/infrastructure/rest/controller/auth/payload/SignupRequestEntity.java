package com.zak.infrastructure.rest.controller.auth.payload;

import com.zak.domain.model.Account;
import com.zak.domain.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestEntity {

//    private String username;
//    private String email;
//    private String password;
//    private Set<String> role = new HashSet<String>();
    private User user;
    private Account account;
}
