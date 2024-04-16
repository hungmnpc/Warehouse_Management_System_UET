package com.monopoco.authservice.repository;

import com.monopoco.authservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByIsDeletedIsFalseAndRoleName(String roleName);

    @Query(value = "select role.level from RoleEntity role where role.roleName = :roleName")
    Integer getLevel(@Param("roleName") String roleName);

    List<RoleEntity> findRoleEntitiesByIsDeletedIsFalseAndLevelGreaterThanOrderByLevelAsc(Integer level);
}
