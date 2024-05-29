package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseEntityRepository extends JpaRepository<WarehouseEntity, UUID> {

    List<WarehouseEntity> findAllByIsDeletedIsFalse();

    Optional<WarehouseEntity> findByIsDeletedIsFalseAndId(UUID id);

    Optional<WarehouseEntity> findByIsDeletedIsFalseAndWarehouseName(String warehouseName);


    @Query(value = "select count(*) from tb_purchase_orders\n" +
            "where warehouse_id = :warehouseId  \n" +
            "and is_deleted = false", nativeQuery = true)
    long countAllPurchaseOrderOfWarehouse(@Param(value = "warehouseId") UUID warehouseId);

    @Query(value = "select count(*) from tb_purchase_orders\n" +
            "where warehouse_id = :warehouseId  \n" +
            "and is_deleted = false \n" +
            "and (status = 'DRAFT') ", nativeQuery = true)
    long countNeedHandlePurchaseOrderOfWarehouse(@Param(value = "warehouseId") UUID warehouseId);


    @Query(value = "select count(*) from tb_purchase_orders\n" +
            "            where warehouse_id = :warehouseId\n" +
            "            and is_deleted = false\n" +
            "            and (status = 'RECEIVED_AND_REQUIRES_WAREHOUSING' or status ='WAREHOUSING' or status= 'STOCKED')", nativeQuery = true)
    long countGoodReceived(@Param(value = "warehouseId") UUID warehouseId);

    @Query(value = "select count(*) from tb_purchase_orders\n" +
            "            where warehouse_id = :warehouseId\n" +
            "            and is_deleted = false\n" +
            "            and (status = 'RECEIVED_AND_REQUIRES_WAREHOUSING')", nativeQuery = true)
    long countGoodReceivedNeedHandle(@Param(value = "warehouseId") UUID warehouseId);


}
