package com.zak.persistence.jpa.repository;

import com.zak.persistence.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserActivityRepository extends JpaRepository<UserActivityEntity, Long> {

    Set<UserActivityEntity> findByUser(UserEntity user);
}
