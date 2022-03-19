package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.UsersUsersAssociationService;
import com.zak.domain.model.UsersUsersAssociation;
import com.zak.domain.model.util.Criteria;
import com.zak.domain.model.util.PageGenerics;
import com.zak.domain.model.util.ResponseContent;
import com.zak.infrastructure.rest.controller.auth.payload.MessageResponseEntity;
import com.zak.infrastructure.rest.controller.resource.dto.UsersUsersAssociationDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/usersAssociation")
public class UsersUsersAssociationController {

    private final UsersUsersAssociationService usersUsersAssociationService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public UsersUsersAssociationController(UsersUsersAssociationService usersUsersAssociationService,
                                           MapperFacade orikaMapperFacade) {
        this.usersUsersAssociationService = usersUsersAssociationService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @GetMapping("/find/{associationId}")
    UsersUsersAssociationDto findUsersAssociationByAssociationId(@PathVariable String associationId) {
        return orikaMapperFacade.map(
                usersUsersAssociationService.findByAssociationId(associationId),
                UsersUsersAssociationDto.class);
    }

    @GetMapping("inviter/{idUser}")
    Set<UsersUsersAssociationDto> findUsersAssociationByInviter(@PathVariable String idUser) {
        return orikaMapperFacade.mapAsSet(
                usersUsersAssociationService.findByInviter(idUser),
                UsersUsersAssociationDto.class);
    }

    @GetMapping("list-invited-user/account/{accountId}/inviter/{inviterId}")
    Set<UsersUsersAssociationDto> getListOfInvitedUsersByAccountIdByInviter(@PathVariable String inviterId,
                                                                            @PathVariable String accountId) {
        return orikaMapperFacade.mapAsSet(
                usersUsersAssociationService.getListOfInvitedUsersByAccountIdByInviter(accountId, inviterId),
                UsersUsersAssociationDto.class);
    }

    @GetMapping("invited-user/account/{accountId}")
    Set<UsersUsersAssociationDto> getListOfInvitedUsersByAccountId(@PathVariable String accountId) {
        return orikaMapperFacade.mapAsSet(
                usersUsersAssociationService.getListOfInvitedUsersByAccountId(accountId),
                UsersUsersAssociationDto.class);
    }

    @GetMapping("page-invited-user/account/{accountId}/criteria")
    PageGenerics<UsersUsersAssociationDto> getPageOfInvitedUsersByInviter(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "") String str,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy) {
        PageGenerics<UsersUsersAssociation> pageOfInvitedUsersByInviter =
                usersUsersAssociationService.getPageOfInvitedUsersByAccountIdByInviter(
                        accountId,
                        str,
                        page,
                        size,
                        sortBy);

        PageGenerics<UsersUsersAssociationDto> pageOfInvitedUsers =
                new PageGenerics<UsersUsersAssociationDto>();

        pageOfInvitedUsers.setPageable(new Criteria(page, size));
        pageOfInvitedUsers.setFirst(pageOfInvitedUsersByInviter.getActualPage() == 0);
        pageOfInvitedUsers
                .setLast(pageOfInvitedUsersByInviter.getActualPage() + 1 ==
                        pageOfInvitedUsersByInviter.getTotalPages());

        pageOfInvitedUsers.setData(
                orikaMapperFacade.mapAsSet(
                        pageOfInvitedUsersByInviter.getData(),
                        UsersUsersAssociationDto.class));
        pageOfInvitedUsers.setSize(pageOfInvitedUsersByInviter.getSize());
        pageOfInvitedUsers.setActualPage(pageOfInvitedUsersByInviter.getActualPage());
        pageOfInvitedUsers.setTotalPages(pageOfInvitedUsersByInviter.getTotalPages());
        pageOfInvitedUsers.setTotalData(pageOfInvitedUsersByInviter.getTotalData());

        return pageOfInvitedUsers;
    }

    @PostMapping("/accept-association/{associationId}/{response}")
    public ResponseContent<UsersUsersAssociationDto> AcceptAssociation(@PathVariable final String associationId,
                                                                       @PathVariable final Boolean response) {
        UsersUsersAssociation usersUsersAssociation = usersUsersAssociationService.acceptAssociation(
                associationId, response);
        return new ResponseContent(
                orikaMapperFacade.map(usersUsersAssociation, UsersUsersAssociationDto.class),
                usersUsersAssociation.isEnabled() ? "Association Acctepted" : "Association refused");
    }


    @PostMapping("/delete-user-assoc/{associationId}")
    public ResponseEntity<?> addRoleToUserAssociation(@PathVariable final String associationId) {
        usersUsersAssociationService.deleteUserAssociation(associationId);
        return ResponseEntity.ok(new MessageResponseEntity("User deleted successfully"));
    }

    @PostMapping("/add-role-to-user-assoc/{associationId}/{role}")
    public ResponseContent<?> addRoleToUserAssociation(@PathVariable final String associationId,
                                                       @PathVariable final String role) {
        UsersUsersAssociation assoc =
                usersUsersAssociationService.addRoleToUserAssociation(associationId, role);
        return new ResponseContent(assoc, "Role added successfully");
    }

    @PostMapping("/delete-role-from-user-assoc/{associationId}/{role}")
    public ResponseContent<?> deleteRoleFromUserAssociation(@PathVariable final String associationId,
                                                            @PathVariable final String role) {
        UsersUsersAssociation assoc =
                usersUsersAssociationService.deleteRoleFromUserAssociation(associationId, role);
        return new ResponseContent(assoc, "Role deleted successfully");
    }

    @GetMapping("/association/invitedUser/{invitedUserId}")
    public Set<UsersUsersAssociationDto> getAssocByInvitedUserId(@PathVariable final String invitedUserId) {
        return orikaMapperFacade.mapAsSet(usersUsersAssociationService.findByInvitedUser(invitedUserId),
                UsersUsersAssociationDto.class);
    }

}
