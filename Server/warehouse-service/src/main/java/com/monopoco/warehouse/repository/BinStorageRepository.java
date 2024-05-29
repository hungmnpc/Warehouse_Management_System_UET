package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.BinStorage;
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
 * Time: 11:33
 */
@Repository
public interface BinStorageRepository extends JpaRepository<BinStorage, UUID> {

    List<BinStorage> findAllByRackLevelStorageIdOrderByBinName(UUID rackLevelStorageId);

    Optional<BinStorage> findByBarcodeAndWarehouseId(String barcode, UUID warehouseId);
}