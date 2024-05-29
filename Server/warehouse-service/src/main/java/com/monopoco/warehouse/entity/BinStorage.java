package com.monopoco.warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.entity
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:13
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_bin_storages")
public class BinStorage {

    @Id
    @Column(name = "bin_id")
    private UUID binId;

    @Column(name = "bin_name")
    private String binName;

    @Column(name = "disable")
    private Boolean disable = false;

    @Column(name = "occupied")
    private Boolean occupied = false;

    private String barcode;

    @Column(name = "rack_level_storage_id")
    private UUID rackLevelStorageId;

    @Column(name = "floor_storage_id")
    private UUID floorStorageId;

    @Column(name = "warehouseId")
    private UUID warehouseId;

    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "multiple_product")
    private Boolean isStorageMultipleProduct;
}
