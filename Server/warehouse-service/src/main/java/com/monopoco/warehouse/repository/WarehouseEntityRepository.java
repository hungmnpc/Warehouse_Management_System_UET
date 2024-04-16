package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, UUID> {

    List<WarehouseEntity> findAllByIsDeletedIsFalse();

    Optional<WarehouseEntity> findByIsDeletedIsFalseAndId(UUID id);
}
