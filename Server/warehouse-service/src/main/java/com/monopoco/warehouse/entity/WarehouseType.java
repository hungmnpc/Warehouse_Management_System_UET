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
 * Date: 30/03/2024
 * Time: 00:10
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "warehouse_types")
@Entity
public class WarehouseType extends AuditEntity<UUID>{

    @Id
    private UUID id;

    @Column(name = "wht_name")
    private String warehouseTypeName;

    @Column(name = "wht_description", columnDefinition= "TEXT")
    private String warehouseTypeDescription;


}
