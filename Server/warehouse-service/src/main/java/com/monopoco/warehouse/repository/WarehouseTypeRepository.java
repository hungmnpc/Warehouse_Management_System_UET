package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.WarehouseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseTypeRepository extends JpaRepository<WarehouseType, UUID> {

    Optional<WarehouseType> findByIsDeletedIsFalseAndWarehouseTypeName(String warehouseTypeName);

    List<WarehouseType> findAllByIsDeletedIsFalse();

    Optional<WarehouseType> findByIsDeletedIsFalseAndId(UUID id);

}
