package com.monopoco.common.model;

import com.monopoco.common.model.inventory.ProductBinInventoryInterface;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 18:14
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductInventoryDTO {

    private UUID id;

    private UUID productId;

    private String productName;

    private Long quantityAvailable;

    private Long minimumStockLevel;

    private Long maximumStockLevel;

    private Long reorderPoint;

    private UUID warehouseId;

    private List<ProductBinInventoryInterface> detail;


    @QueryProjection
    public ProductInventoryDTO(UUID id, UUID productId, String productName, Long quantityAvailable, Long minimumStockLevel, Long maximumStockLevel, Long reorderPoint, UUID warehouseId) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantityAvailable = quantityAvailable;
        this.minimumStockLevel = minimumStockLevel;
        this.maximumStockLevel = maximumStockLevel;
        this.reorderPoint = reorderPoint;
        this.warehouseId = warehouseId;
    }
}
