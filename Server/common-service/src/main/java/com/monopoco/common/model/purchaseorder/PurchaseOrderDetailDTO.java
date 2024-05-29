package com.monopoco.common.model.purchaseorder;

import com.monopoco.common.model.AuditDTO;
import com.monopoco.common.model.ProductDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.service.dto
 * Author: hungdq
 * Date: 27/04/2024
 * Time: 10:30
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class PurchaseOrderDetailDTO extends AuditDTO {

    private UUID purchaseOrderDetailId;

    private UUID purchaseOrderId;

    private ProductDTO product;

    private Long quantity;

    private Long stockedQuantity;



    @QueryProjection
    public PurchaseOrderDetailDTO(UUID purchaseOrderDetailId, UUID purchaseOrderId, ProductDTO product, Long quantity, Long stockedQuantity) {
        this.purchaseOrderDetailId = purchaseOrderDetailId;
        this.purchaseOrderId = purchaseOrderId;
        this.product = product;
        this.quantity = quantity;
        this.stockedQuantity = stockedQuantity;
    }
}
