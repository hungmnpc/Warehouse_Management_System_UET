package com.monopoco.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.entity
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 12:42
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_product_inventory")
public class ProductInventory extends AuditEntity<UUID>{

    @Id
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "quantity_available")
    private Long quantityAvailable;

    @Column(name = "minimum_stock_level")
    private Long minimumStockLevel;

    @Column(name = "maximum_stock_level")
    private Long maximumStockLevel;

    @Column(name = "reorder_point")
    private Long reorderPoint;

    @Column(name = "warehouse_id")
    private UUID warehouseId;
}
