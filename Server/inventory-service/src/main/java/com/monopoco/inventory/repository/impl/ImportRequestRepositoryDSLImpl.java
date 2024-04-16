package com.monopoco.inventory.repository.impl;

import com.monopoco.inventory.entity.QImportRequest;
import com.monopoco.inventory.entity.QWarehouseEntity;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.repository.ImportRequestRepositoryDSL;
import com.monopoco.inventory.response.PageResponse;
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

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository.impl
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:17
 */

@Repository
@Transactional
public class ImportRequestRepositoryDSLImpl implements ImportRequestRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QImportRequest importRequest = QImportRequest.importRequest;

    private final QWarehouseEntity warehouseEntity = QWarehouseEntity.warehouseEntity;

    @Override
    public PageResponse<List<ImportRequestDTO>> searchOrder(ImportRequestFilter filter, Pageable pageable) {
        JPAQuery<ImportRequestDTO> query = new JPAQuery<>(entityManager)
                .select(new QImportRequestDTO(
                        importRequest.createdBy,
                        importRequest.createdDate,
                        importRequest.lastModifiedBy,
                        importRequest.lastModifiedDate,
                        importRequest.isDeleted,
                        importRequest.id,
                        warehouseEntity.id,
                        warehouseEntity.warehouseName,
                        importRequest.poCode,
                        importRequest.importRequestCode,
                        importRequest.deliveryDate,
                        importRequest.estimatedArrivalDate,
                        importRequest.status.stringValue()
                        )
                )
                .from(importRequest)
                .leftJoin(warehouseEntity)
                .on(importRequest.warehouseId.eq(warehouseEntity.id))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(importRequest.isDeleted.isFalse());
        if (filter != null) {
            if (filter.getWarehouseName() != null) {
                booleanBuilder.and(warehouseEntity.warehouseName.containsIgnoreCase(filter.getWarehouseName()));
            }
            if (!StringUtils.isEmpty(filter.getOrderBy())) {
                Path<Object> fieldPath = Expressions.path(Object.class, warehouseEntity, filter.getOrderBy());
                if (filter.getOrderType().equalsIgnoreCase("asc")) {
                    query.orderBy(new OrderSpecifier(Order.ASC, fieldPath));
                } else {
                    query.orderBy(new OrderSpecifier(Order.DESC, fieldPath));
                }
            }
        }
        query.where(booleanBuilder);
        List<ImportRequestDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<ImportRequestDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
