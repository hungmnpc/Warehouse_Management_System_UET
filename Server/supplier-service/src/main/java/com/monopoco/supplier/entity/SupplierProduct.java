package com.monopoco.supplier.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.entity
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:43
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_supplier-products")
public class SupplierProduct extends AuditEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "supplier_id")
    private UUID supplierId;
}
