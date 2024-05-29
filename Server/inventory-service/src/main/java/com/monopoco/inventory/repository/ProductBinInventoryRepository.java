package com.monopoco.inventory.repository;


import com.monopoco.common.model.inventory.ProductBinInventoryInterface;
import com.monopoco.inventory.entity.ProductBinInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository
 * Author: hungdq
 * Date: 13/05/2024
 * Time: 13:49
 */

@Repository
public interface ProductBinInventoryRepository extends JpaRepository<ProductBinInventory, UUID> {

    Optional<ProductBinInventory> findByBinIdAndProductId(UUID binId, UUID productId);

    @Query(value = "select  pbi.id  as id, pbi.bin_id  as binId,\n" +
            "                pbi.product_id as productId, pbi.quantity_id as quantity, pbi.bin_name as binName,\n" +
            "                    p.product_name as productName,\n" +
            "                    wh.wh_name as warehouseName,\n" +
            "                    IF(bs.floor_storage_id is null, true, false) as isRackStorage,\n" +
            "                    lvr.rack_storage_id as rackId, bs.floor_storage_id as floorId,\n" +
            "                    aw.area_name as areaName from tb_product_bin_inventory pbi\n" +
            "            inner join tb_bin_storages bs on pbi.bin_id = bs.bin_id\n" +
            "            inner join warehouses wh on bs.warehouse_id = wh.id\n" +
            "            inner join tb_products p on p.product_id = pbi.product_id\n" +
            "            inner join tb_area_warehouses aw on aw.id = bs.area_id\n" +
            "            left join tb_level_rack_storages lvr on lvr.level_rack_id = bs.rack_level_storage_id\n" +
            "            where (:warehouseId is null or wh.id = :warehouseId)\n" +
            "            and (:productId is not null && p.product_id = :productId)", nativeQuery = true)
    List<ProductBinInventoryInterface> getAllProductInBinWarehouseByProductName(@Param(value = "warehouseId") UUID warehouseId,
                                                                                @Param(value = "productId") UUID productId);

    List<ProductBinInventory> findAllByBinId(UUID binId);
}