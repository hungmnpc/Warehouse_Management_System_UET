package com.monopoco.common.model.purchaseorder;

import com.monopoco.common.model.AuditDTO;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository.service.dto
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:14
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class PurchaseOrderDTO extends AuditDTO {

    private UUID id;

    private String status;

    private String poCode;

    private String referenceNumber;

    private LocalDate inboundDate;

    private LocalDate arrivalDate;

    private String comment;

    private UUID supplierId;

    private String supplierName;

    private UUID warehouseId;

    private String warehouseName;

    private String employeeFullName;

    private String employeeName;

    private UUID employeeId;

    private LocalDate deadLineToStock;

    @QueryProjection
    public PurchaseOrderDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                            LocalDateTime lastModifiedDate, Boolean isDeleted,
                            UUID id, Status status, String poCode, String referenceNumber,
                            LocalDate inboundDate, LocalDate arrivalDate, String comment,
                            UUID supplierId, String supplierName, UUID warehouseId,
                            String warehouseName, UUID employeeId, LocalDate deadLineToStock) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.status = status.toString();
        this.poCode = poCode;
        this.referenceNumber = referenceNumber;
        this.inboundDate = inboundDate;
        this.arrivalDate = arrivalDate;
        this.comment = comment;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.employeeId = employeeId;
        this.deadLineToStock = deadLineToStock;
    }
}
