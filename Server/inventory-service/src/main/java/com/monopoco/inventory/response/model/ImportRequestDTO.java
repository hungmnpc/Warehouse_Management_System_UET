package com.monopoco.inventory.response.model;

import com.monopoco.inventory.entity.Status;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.response.model
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 16:31
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class ImportRequestDTO extends AuditDTO{


    private UUID id;

    private UUID warehouseId;

    private String warehouseName;

    private String poCode;

    private String importRequestCode;

    private LocalDate deliveryDate;

    private LocalDate estimatedArrivalDate;

    private String status;

    private UserDTO employeeId1;

    private UserDTO employeeId2;

    private UserDTO employeeId3;

    private UserDTO employeeId4;

    @QueryProjection
    public ImportRequestDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID id, UUID warehouseId, String warehouseName, String poCode, String importRequestCode, LocalDate deliveryDate, LocalDate estimatedArrivalDate, String status) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.poCode = poCode;
        this.importRequestCode = importRequestCode;
        this.deliveryDate = deliveryDate;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.status = status;
    }
}
