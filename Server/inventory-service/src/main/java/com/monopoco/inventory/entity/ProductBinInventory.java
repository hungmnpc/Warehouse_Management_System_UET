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
 * Date: 11/05/2024
 * Time: 15:37
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_product_bin_inventory")
@Entity
public class    ProductBinInventory {

    @Id
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "bin_name")
    private String binName;

    @Column(name = "bin_id")
    private UUID binId;

    @Column(name = "quantity_id")
    private Integer quantity;
}
