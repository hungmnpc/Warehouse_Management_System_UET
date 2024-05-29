package com.monopoco.purchaseorder.entity;

import com.monopoco.common.model.purchaseorder.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.entity
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 16:44
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_purchase_orders")
public class PurchaseOrder extends AuditEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "po_code")
    private String poCode;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "inbound_date")
    private LocalDate inboundDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "dead_line_to_stocked")
    private LocalDate deadLineToStocked;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "warehouse_id")
    private UUID warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;
}

