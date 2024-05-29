package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.Aisle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 17:32
 */

@Repository
public interface AisleRepository extends JpaRepository<Aisle, UUID> {
    Optional<Aisle> findByAisleNameAndAreaId(String aisleName, UUID areaId);
}