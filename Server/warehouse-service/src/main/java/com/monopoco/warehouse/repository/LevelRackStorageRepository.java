package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.LevelRackStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 11:32
 */

@Repository
public interface LevelRackStorageRepository extends JpaRepository<LevelRackStorage, UUID> {

    List<LevelRackStorage> findAllByRackStorageIdOrderByLevel(UUID rackStorageId);

    Optional<LevelRackStorage> findByLevelNameAndRackStorageId(String levelName, UUID rackStorageId);
}
