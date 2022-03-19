package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.UsersUsersAssociationService;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.infrastructure.rest.controller.resource.dto.UsersUsersAssociationDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/role")
public class RoleController {

    private final UsersUsersAssociationService usersUsersAssociationService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RoleController(UsersUsersAssociationService usersUsersAssociationService,
                          MapperFacade orikaMapperFacade) {
        this.usersUsersAssociationService = usersUsersAssociationService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/user/{userId}")
    public Set<UsersUsersAssociationDto> getUserRolesByUserId(@PathVariable final String userId) {
        Set<UsersUsersAssociation> assocList = usersUsersAssociationService.getUserRolesByUserId(userId);
        return orikaMapperFacade.mapAsSet(assocList, UsersUsersAssociationDto.class);
    }
}
