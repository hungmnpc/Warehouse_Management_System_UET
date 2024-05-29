package com.monopoco.purchaseorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.entity
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 16:54
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_purchase_order_details")
public class PurchaseOrderDetail extends AuditEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "purchase_order_id")
    private UUID purchaseOrderId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "stockedQuantity")
    private Long stockedQuanity;
}
