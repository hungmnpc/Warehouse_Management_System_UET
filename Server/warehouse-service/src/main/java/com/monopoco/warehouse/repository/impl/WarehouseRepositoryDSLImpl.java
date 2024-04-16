package com.monopoco.warehouse.repository.impl;

import com.monopoco.warehouse.entity.QWarehouseEntity;
import com.monopoco.warehouse.entity.QWarehouseType;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.repository.WarehouseRepositoryDSL;
import com.monopoco.warehouse.response.PageResponse;
import com.monopoco.warehouse.response.model.QWarehouseDTO;
import com.monopoco.warehouse.response.model.UserDTO;
import com.monopoco.warehouse.response.model.WarehouseDTO;
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
 * Package: com.monopoco.warehouse.repository.impl
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 18:50
 */

@Repository
@Transactional
public class WarehouseRepositoryDSLImpl implements WarehouseRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QWarehouseEntity warehouseEntity = QWarehouseEntity.warehouseEntity;

    private final QWarehouseType warehouseType = QWarehouseType.warehouseType;
    @Override
    public PageResponse<List<WarehouseDTO>> searchOrder(WarehouseFilter filter, Pageable pageable) {
        JPAQuery<WarehouseDTO> query = new JPAQuery<>(entityManager)
                .select(new QWarehouseDTO(
                        warehouseEntity.createdBy,
                        warehouseEntity.createdDate,
                        warehouseEntity.lastModifiedBy,
                        warehouseEntity.lastModifiedDate,
                        warehouseEntity.isDeleted,
                        warehouseEntity.id,
                        warehouseEntity.warehouseName,
                        warehouseEntity.warehouseNameAcronym,
                        warehouseEntity.location,
                        warehouseType.warehouseTypeName,
                        warehouseEntity.provinceId,
                        warehouseEntity.provinceName,
                        warehouseEntity.districtId,
                        warehouseEntity.districtName,
                        warehouseEntity.wardId,
                        warehouseEntity.wardName
                        )
                )
                .from(warehouseEntity)
                .innerJoin(warehouseType)
                .on(warehouseEntity.warehouseTypeId.eq(warehouseType.id))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(warehouseEntity.isDeleted.isFalse());
        if (filter != null) {
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
        List<WarehouseDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<WarehouseDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
