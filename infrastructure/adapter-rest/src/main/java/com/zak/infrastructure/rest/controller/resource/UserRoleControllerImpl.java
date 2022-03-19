package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.UserRoleService;
import com.zak.domain.enums.EUserRole;
import com.zak.domain.model.UserRole;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/userRole")
public class UserRoleControllerImpl {

    private final UserRoleService userRoleService;

    public UserRoleControllerImpl(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }

    @GetMapping("/{name}")
    Optional<UserRole> findByName(@PathVariable EUserRole name) {
        return userRoleService.findByName(name);
    }
}
