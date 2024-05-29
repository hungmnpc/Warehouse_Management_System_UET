package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.BinConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository
 * Author: hungdq
 * Date: 12/05/2024
 * Time: 00:40
 */

@Repository
public interface BinConfigurationRepository extends JpaRepository<BinConfiguration, UUID> {

    Optional<BinConfiguration> findByBinId(UUID binId);
}