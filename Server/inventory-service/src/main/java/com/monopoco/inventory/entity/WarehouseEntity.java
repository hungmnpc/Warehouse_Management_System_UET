package com.monopoco.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.entity
 * Author: hungdq
 * Date: 29/03/2024
 * Time: 23:56
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "warehouses")
@Entity
public class WarehouseEntity extends AuditEntity<UUID>{

    @Id
    private UUID id;

    @Column(name = "wh_name")
    private String warehouseName;

    @Column(name = "wh_name_acronym")
    @Comment("Tên kho viết tắt")
    private String warehouseNameAcronym;

    @Column(name = "location")
    private String location;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "ward_id")
    private String wardId;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "wh_type_id")
    private UUID warehouseTypeId;
}
