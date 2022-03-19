package com.zak.persistence.jpa.adapter;

import com.zak.domain.enums.EUserRole;
import com.zak.domain.model.User;
import com.zak.domain.model.UserRole;
import com.zak.domain.spi.UserRolePersistencePort;
import com.zak.persistence.jpa.entity.UserRoleEntity;
import com.zak.persistence.jpa.repository.UserRoleRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRoleAdapter implements UserRolePersistencePort {

    private UserRoleRepository userRoleRepository;

    @Autowired
    private MapperFacade orikaMapperFacade;

    public UserRoleAdapter(UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Optional<UserRole> findByName(EUserRole name) {
        Optional<UserRoleEntity> roleEntity =  userRoleRepository.findByName(name);
        UserRole userRole = orikaMapperFacade.map(roleEntity.get(), UserRole.class);
        return Optional.of(userRole);
    }
}
