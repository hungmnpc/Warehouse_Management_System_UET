package com.monopoco.product.response.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.response.model
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 15:43
 */

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class ProductCategoryDTO extends AuditDTO{

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID productCategoryId;

    private String categoryName;

    private String categoryDescription;

    @QueryProjection
    public ProductCategoryDTO(UUID productCategoryId, String categoryName, String categoryDescription) {
        this.productCategoryId = productCategoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
}
