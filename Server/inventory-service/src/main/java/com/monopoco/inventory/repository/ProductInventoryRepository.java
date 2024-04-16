package com.monopoco.inventory.repository;


import com.monopoco.inventory.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, UUID> {

    Optional<ProductInventory> findByIsDeletedIsFalseAndId(UUID id);

    Optional<ProductInventory> findByIsDeletedIsFalseAndProductIdAndWarehouseId(UUID productId, UUID warehouseId);
}
