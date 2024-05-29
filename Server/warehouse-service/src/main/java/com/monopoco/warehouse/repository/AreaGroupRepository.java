package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.AreaGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:27
 */

@Repository
public interface AreaGroupRepository extends JpaRepository<AreaGroup, UUID> {

    Optional<AreaGroup> findByAreaGroupId(String areaGroupId);
}