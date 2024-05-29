package com.monopoco.purchaseorder.repository.impl;

import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.QProductCategoryDTO;
import com.monopoco.common.model.QProductDTO;
import com.monopoco.common.model.purchaseorder.*;
import com.monopoco.purchaseorder.entity.*;
import com.monopoco.purchaseorder.repository.PurchaseOrderRepositoryDSL;
import com.monopoco.purchaseorder.service.filter.ProductFilter;
import com.monopoco.purchaseorder.service.filter.SearchPurchaseOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository.impl
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 16:50
 */

@Repository
@Transactional
public class PurchaseOrderRepositoryDSLImpl implements PurchaseOrderRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QPurchaseOrder purchaseOrder = QPurchaseOrder.purchaseOrder;

    private final QPurchaseOrderDetail purchaseOrderDetail = QPurchaseOrderDetail.purchaseOrderDetail;

    private final QProduct product = QProduct.product;

    private QProductCategory productCategory = QProductCategory.productCategory;

    private QUserAssignedPO userAssignedPO = QUserAssignedPO.userAssignedPO;

    @Override
    public PageResponse<List<PurchaseOrderDTO>> searchOrder(SearchPurchaseOrder filter, Pageable pageable) {
        JPAQuery<PurchaseOrderDTO> query = new JPAQuery<>(entityManager)
                .select(new QPurchaseOrderDTO(
                        purchaseOrder.createdBy,
                        purchaseOrder.createdDate,
                        purchaseOrder.lastModifiedBy,
                        purchaseOrder.lastModifiedDate,
                        purchaseOrder.isDeleted,
                        purchaseOrder.id,
                        purchaseOrder.status,
                        purchaseOrder.poCode,
                        purchaseOrder.referenceNumber,
                        purchaseOrder.inboundDate,
                        purchaseOrder.arrivalDate,
                        purchaseOrder.comment,
                        purchaseOrder.supplierId,
                        purchaseOrder.supplierName,
                        purchaseOrder.warehouseId,
                        purchaseOrder.warehouseName,
                        userAssignedPO.userId,
                        purchaseOrder.deadLineToStocked
                ) )
                .from(purchaseOrder)
                .leftJoin(userAssignedPO)
                .on(userAssignedPO.poId.eq(purchaseOrder.id))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(purchaseOrder.createdDate.desc());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(purchaseOrder.isDeleted.isFalse());
        if (filter != null) {
            if (!StringUtils.isEmpty(filter.getPoCode())) {
                booleanBuilder.and(purchaseOrder.poCode.containsIgnoreCase(filter.getPoCode()));
            }
            if (!StringUtils.isEmpty(filter.getReferenceNumber())) {
                booleanBuilder.and(purchaseOrder.referenceNumber.containsIgnoreCase(filter.getReferenceNumber()));
            }
            if (filter.getStatus() != null) {
                List<Status> statuses = filter.getStatus().stream().map(
                        status -> Status.of(status)
                ).toList();
                booleanBuilder.and(purchaseOrder.status.in(statuses));
            }
            if (filter.getArrivalDateFrom() != null) {
                booleanBuilder.and(purchaseOrder.arrivalDate.after(filter.getArrivalDateFrom()));
            }
            if (filter.getArrivalDateTo() != null) {
                booleanBuilder.and(purchaseOrder.arrivalDate.before(filter.getArrivalDateTo()));
            }
            if (filter.getWarehouseId() != null) {
                booleanBuilder.and(purchaseOrder.warehouseId.eq(filter.getWarehouseId()));
            }
        }
        query.where(booleanBuilder);
        List<PurchaseOrderDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<PurchaseOrderDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }

    @Override
    public PageResponse<List<PurchaseOrderDetailDTO>> searchOrderProductDTO(UUID purchaseOrderId, ProductFilter filter, Pageable pageable) {

        JPAQuery<PurchaseOrderDetailDTO> query = new JPAQuery<>(entityManager)
                .select(new QPurchaseOrderDetailDTO(
                        purchaseOrderDetail.id,
                        purchaseOrderDetail.purchaseOrderId,
                        new QProductDTO(
                                product.createdBy,
                                product.createdDate,
                                product.lastModifiedBy,
                                product.lastModifiedDate,
                                product.isDeleted,
                                product.productId,
                                product.productCode,
                                product.productName,
                                product.barcode,
                                product.productDescription,
                                new QProductCategoryDTO(
                                        productCategory.productCategoryId,
                                        productCategory.categoryName,
                                        productCategory.categoryDescription
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
                        ),
                        purchaseOrderDetail.quantity,
                        purchaseOrderDetail.stockedQuanity
                ))
                .from(purchaseOrderDetail)
                .innerJoin(product)
                .on(purchaseOrderDetail.productId.eq(product.productId))
                .innerJoin(productCategory)
                .on(product.productCategoryId.eq(productCategory.productCategoryId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(purchaseOrderDetail.isDeleted.isFalse());
        booleanBuilder.and(purchaseOrderDetail.purchaseOrderId.eq(purchaseOrderId));

        if (filter != null) {
            if (!StringUtils.isEmpty(filter.getProductName())) {
                booleanBuilder.and(product.productName.containsIgnoreCase(filter.getProductName()));
            }
        }

        query.where(booleanBuilder);
        List<PurchaseOrderDetailDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<PurchaseOrderDetailDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }

    @Override
    public PageResponse<List<PurchaseOrderDTO>> searchPOForEmployee(UUID warehouseId, UUID employeeId) {
        JPAQuery<PurchaseOrderDTO> query = new JPAQuery<>(entityManager)
                .select(new QPurchaseOrderDTO(
                        purchaseOrder.createdBy,
                        purchaseOrder.createdDate,
                        purchaseOrder.lastModifiedBy,
                        purchaseOrder.lastModifiedDate,
                        purchaseOrder.isDeleted,
                        purchaseOrder.id,
                        purchaseOrder.status,
                        purchaseOrder.poCode,
                        purchaseOrder.referenceNumber,
                        purchaseOrder.inboundDate,
                        purchaseOrder.arrivalDate,
                        purchaseOrder.comment,
                        purchaseOrder.supplierId,
                        purchaseOrder.supplierName,
                        purchaseOrder.warehouseId,
                        purchaseOrder.warehouseName,
                        userAssignedPO.userId,
                        purchaseOrder.deadLineToStocked
                ) )
                .from(purchaseOrder)
                .innerJoin(userAssignedPO)
                .on(userAssignedPO.poId.eq(purchaseOrder.id));


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(purchaseOrder.isDeleted.isFalse());
        booleanBuilder.and(purchaseOrder.warehouseId.eq(warehouseId));
        booleanBuilder.and(userAssignedPO.userId.eq(employeeId));
        List<Status> statuses = new ArrayList<>() {{
            add(Status.RECEIVED_AND_REQUIRES_WAREHOUSING);
            add(Status.WAREHOUSING);
        }};
        booleanBuilder.and(purchaseOrder.status.in(statuses));



        query.where(booleanBuilder);
        List<PurchaseOrderDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<PurchaseOrderDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(0)
                .pageSize((int) count);
    }
}
