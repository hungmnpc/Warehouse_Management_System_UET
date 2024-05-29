package com.monopoco.common.model.purchaseorder;

import com.monopoco.common.model.AuditDTO;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository.service.dto
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:16
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class ProductPurchaseOrderDTO extends AuditDTO {

    private UUID id;

    private UUID productId;

    private UUID purchaseOrderId;

    private Long quantity;
}
