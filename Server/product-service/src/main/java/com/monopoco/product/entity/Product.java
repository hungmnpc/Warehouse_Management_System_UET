package com.monopoco.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.entity
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 15:08
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_products")
@Entity
public class Product extends AuditEntity<UUID>{

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "sku")
    private String sku;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category_id")
    private UUID productCategoryId;

    @Column(name = "reorder_quantity")
    private Integer reorderQuantity;

    @Column(name = "refrigerated")
    private Boolean refrigerated;

    private String unit;

    @Column(name = "is_packed")
    private Boolean isPacked;

    @Column(name = "packed_weight")
    private Double packedWeight;

    @Column(name = "packed_height")
    private Double packedHeight;

    @Column(name = "packed_width")
    private Double packedWidth;

    @Column(name = "packed_depth")
    private Double packedDepth;
}
