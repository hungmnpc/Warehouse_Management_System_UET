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
 * Time: 09:20
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_area_warehouses")
@Entity
public class AreaWarehouse {

    @Id
    private UUID id;

    @Column(name = "area_name")
    private String areaName;

    @Column(name="area_prefix")
    private String areaPrefix;

    @Column(name = "order_area")
    private Integer orderArea;

    @Column(name = "warehouse_id")
    private UUID warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "area_group_id")
    private UUID areaGroupId;
}
