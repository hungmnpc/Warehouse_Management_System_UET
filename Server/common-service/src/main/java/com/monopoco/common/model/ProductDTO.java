package com.monopoco.common.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.response.model
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 16:22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class ProductDTO extends AuditDTO{

    private UUID productId;

    private String productCode;

    private String productName;

    private String barcode;

    private String sku;

    private String productDescription;

    private DropDown<UUID, String> productCategory;

    private ProductCategoryDTO productCategoryDTO;

    private Integer reorderQuantity;

    private Boolean refrigerated;

    private String unit;

    private Boolean isPacked;

    private Double packedWeight;

    private Double packedHeight;

    private Double packedWidth;

    private Double packedDepth;

    @QueryProjection
    public ProductDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID productId, String productCode, String productName, String barcode, String productDescription, ProductCategoryDTO productCategoryDTO, Integer reorderQuantity, Boolean refrigerated, String unit, Boolean isPacked, Double packedWeight, Double packedHeight, Double packedWidth, Double packedDepth, String sku) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.barcode = barcode;
        this.productDescription = productDescription;
        this.productCategoryDTO = productCategoryDTO;
        this.reorderQuantity = reorderQuantity;
        this.refrigerated = refrigerated;
        this.unit = unit;
        this.isPacked = isPacked;
        this.packedWeight = packedWeight;
        this.packedHeight = packedHeight;
        this.packedWidth = packedWidth;
        this.packedDepth = packedDepth;
        this.sku = sku;
    }

}
