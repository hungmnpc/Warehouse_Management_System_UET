package com.monopoco.purchaseorder.entity;

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
 * Time: 15:22
 */

@Entity
@Table(name = "tb_product_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductCategory extends AuditEntity<UUID>{

    @Id
    @Column(name = "product_category_id")
    private UUID productCategoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;
}
