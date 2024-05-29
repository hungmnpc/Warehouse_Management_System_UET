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
 * Time: 17:40
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_suppliers")
public class Supplier extends AuditEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_number")
    private String supplierNumber;

    @Column(name = "supplier_address1")
    private String supplierAddress1;

    @Column(name = "supplier_address2")
    private String supplierAddress2;
}
