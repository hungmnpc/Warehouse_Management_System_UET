package com.monopoco.inventory.repository.impl;

import com.monopoco.common.model.PageResponse;
//import com.monopoco.common.model.ProductInventoryDTO;
import com.monopoco.common.model.ProductInventoryDTO;
import com.monopoco.common.model.QProductCategoryDTO;
import com.monopoco.common.model.QProductInventoryDTO;
import com.monopoco.inventory.entity.QImportRequest;
import com.monopoco.inventory.entity.QProduct;
import com.monopoco.inventory.entity.QProductInventory;
import com.monopoco.inventory.entity.QWarehouseEntity;
import com.monopoco.inventory.repository.ProductInventoryRepositoryDSL;
import com.monopoco.inventory.response.model.ImportRequestDTO;
import com.monopoco.inventory.response.model.QImportRequestDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository.impl
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 18:20
 */

@Repository
@Transactional
public class ProductInventoryRepositoryDSLImpl implements ProductInventoryRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QProductInventory productInventory = QProductInventory.productInventory;

    private final QProduct product = QProduct.product;

    @Override
    public PageResponse<List<ProductInventoryDTO>> searchOrder(String productName, UUID warehouseId, Pageable pageable) {
        JPAQuery<ProductInventoryDTO> query = new JPAQuery<>(entityManager)
                .select(new QProductInventoryDTO(
                        productInventory.id,
                        productInventory.productId,
                        product.productName,
                        productInventory.quantityAvailable,
                        productInventory.minimumStockLevel,
                        productInventory.maximumStockLevel,
                        productInventory.reorderPoint,
                        productInventory.warehouseId
                        )
                )
                .from(productInventory)
                .leftJoin(product)
                .on(product.productId.eq(productInventory.productId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(productInventory.isDeleted.isFalse());

            if (!StringUtils.isEmpty(productName)) {
                booleanBuilder.and(product.productName.containsIgnoreCase(productName));
            }
            if (warehouseId != null) {
                booleanBuilder.and(productInventory.warehouseId.eq(warehouseId));
            }
        query.where(booleanBuilder);
        List<ProductInventoryDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<ProductInventoryDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
