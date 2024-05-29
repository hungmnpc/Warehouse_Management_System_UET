package com.monopoco.supplier.service.dto;

import com.monopoco.supplier.response.model.AuditDTO;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service.dto
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:49
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class SupplierDTO extends AuditDTO {

    private UUID id;

    private String supplierName;

    private String supplierNumber;

    private String supplierAddress1;

    private String supplierAddress2;

    @QueryProjection
    public SupplierDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID id, String supplierName, String supplierNumber, String supplierAddress1, String supplierAddress2) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.supplierName = supplierName;
        this.supplierNumber = supplierNumber;
        this.supplierAddress1 = supplierAddress1;
        this.supplierAddress2 = supplierAddress2;
    }
}
