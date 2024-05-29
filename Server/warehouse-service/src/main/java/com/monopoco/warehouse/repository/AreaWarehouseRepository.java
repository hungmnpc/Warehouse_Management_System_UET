package com.monopoco.warehouse.repository;

import com.monopoco.warehouse.entity.AreaWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:25
 */

@Repository
public interface AreaWarehouseRepository extends JpaRepository<AreaWarehouse, UUID> {

    List<AreaWarehouse> findAllByWarehouseId(UUID warehouseId);

    Optional<AreaWarehouse> findByAreaPrefixAndOrderAreaAndWarehouseId(String areaPrefix, Integer orderArea, UUID warehouseId);


    @Query(value = "select order_area from tb_area_warehouses\n" +
            "where area_prefix = :prefix\n" +
            "order by order_area desc\n" +
            "limit 1", nativeQuery = true)
    Integer getLastOrderAreaPrefix(@Param(value = "prefix") String prefix);
}