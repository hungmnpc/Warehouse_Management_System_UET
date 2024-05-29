package com.monopoco.product.repository.impl;

import com.monopoco.common.model.PageResponse;
import com.monopoco.product.entity.QProduct;
import com.monopoco.product.entity.QProductCategory;
import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.repository.ProductRepositoryDSL;
import com.monopoco.product.response.model.ProductDTO;
import com.monopoco.product.response.model.QProductCategoryDTO;
import com.monopoco.product.response.model.QProductDTO;
import com.querydsl.core.BooleanBuilder;
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
 * Package: com.monopoco.product.repository
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 17:25
 */
@Repository
@Transactional
public class ProductRepositoryDSLImpl implements ProductRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QProduct product = QProduct.product;

    private final QProductCategory category = QProductCategory.productCategory;

    @Override
    public PageResponse<List<ProductDTO>> searchOrder(ProductFilter filter, Pageable pageable, List<UUID> IdNotIn) {
        JPAQuery<ProductDTO> query = new JPAQuery<>(entityManager)
                .select(new QProductDTO(
                        product.createdBy,
                        product.createdDate,
                        product.lastModifiedBy,
                        product.lastModifiedDate,
                        product.isDeleted,
                        product.productId,
                        product.productCode,
                        product.productName,
                        product.barcode ,
                        product.productDescription,
                        new QProductCategoryDTO(
                                category.productCategoryId,
                                category.categoryName,
                                category.categoryDescription
                        ),
                        product.reorderQuantity,
                        product.refrigerated,
                        product.unit,
                        product.isPacked,
                        product.packedWeight,
                        product.packedHeight,
                        product.packedWidth,
                        product.packedDepth,
                        product.sku
                ) )
                .from(product)
                .innerJoin(category)
                .on(product.productCategoryId.eq(category.productCategoryId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(product.isDeleted.isFalse());
        if (filter != null) {
            if (!StringUtils.isEmpty(filter.getProductName())) {
                booleanBuilder.and(product.productName.containsIgnoreCase(filter.getProductName()));
            }
        }
        if (IdNotIn != null) {
                booleanBuilder.and(product.productId.notIn(IdNotIn));
        }
        query.where(booleanBuilder);
        List<ProductDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<ProductDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
