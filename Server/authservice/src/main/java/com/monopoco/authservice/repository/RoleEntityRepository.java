package com.monopoco.authservice.repository;

import com.monopoco.authservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByIsDeletedIsFalseAndRoleName(String roleName);
}
