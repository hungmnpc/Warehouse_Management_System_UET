package com.monopoco.inventory.response.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.response.model
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:21
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class WarehouseDTO extends AuditDTO {

    private UUID id;

    private String warehouseName;

    private String warehouseNameAcronym;

    private String location;

    private String type;

    private String provinceId;

    private String provinceName;

    private String districtId;

    private String districtName;

    private String wardId;

    private String wardName;

    @QueryProjection

    public WarehouseDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID id, String warehouseName, String warehouseNameAcronym, String location, String type, String provinceId, String provinceName, String districtId, String districtName, String wardId, String wardName) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.warehouseName = warehouseName;
        this.warehouseNameAcronym = warehouseNameAcronym;
        this.location = location;
        this.type = type;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
    }
}
