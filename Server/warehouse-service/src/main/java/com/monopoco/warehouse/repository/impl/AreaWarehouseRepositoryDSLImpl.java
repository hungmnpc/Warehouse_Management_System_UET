package com.monopoco.warehouse.repository.impl;

import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.warehouse.area.AisleDTO;
import com.monopoco.common.model.warehouse.area.AreaWarehouseDTO;
import com.monopoco.common.model.warehouse.area.QAisleDTO;
import com.monopoco.common.model.warehouse.area.QAreaWarehouseDTO;
import com.monopoco.warehouse.entity.QAisle;
import com.monopoco.warehouse.entity.QAreaGroup;
import com.monopoco.warehouse.entity.QAreaWarehouse;
import com.monopoco.warehouse.repository.AreaWarehouseRepositoryDSL;
import com.monopoco.warehouse.response.model.QWarehouseDTO;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository.impl
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 13:05
 */

@Repository
@Transactional
public class AreaWarehouseRepositoryDSLImpl implements AreaWarehouseRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QAreaWarehouse areaWarehouse = QAreaWarehouse.areaWarehouse;

    private final QAreaGroup areaGroup = QAreaGroup.areaGroup;

    private final QAisle aisle = QAisle.aisle;
    @Override
    public List<AreaWarehouseDTO> areaWarehouse(UUID warehouseId) {
        JPAQuery<AreaWarehouseDTO> query = new JPAQuery<>(entityManager)
                .select(
                        new QAreaWarehouseDTO(
                                areaWarehouse.id,
                                areaWarehouse.areaName,
                                areaWarehouse.warehouseId,
                                areaWarehouse.warehouseName,
                                areaWarehouse.areaGroupId,
                                areaGroup.areaGroupName,
                                areaWarehouse.areaPrefix.append(areaWarehouse.orderArea.stringValue())
                        )
                )
                .from(areaWarehouse)
                .innerJoin(areaGroup)
                .on(areaWarehouse.areaGroupId.eq(areaGroup.id));

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (warehouseId != null) {
            booleanBuilder.and(areaWarehouse.warehouseId.eq(warehouseId));
        }
        query.where(booleanBuilder);
        List<AreaWarehouseDTO> result = query.fetch();
        return result;
    }

    @Override
    public List<AisleDTO> getAllAisleInArea(UUID areaId) {
        JPAQuery<AisleDTO> query = new JPAQuery<>(entityManager)
                .select(
                        new QAisleDTO(
                             aisle.aisleId,
                             aisle.aisleName,
                             aisle.locationType
                        )
                )
                .from(aisle)
                .orderBy(aisle.aisleName.asc());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (areaId != null) {
            booleanBuilder.and(aisle.areaId.eq(areaId));
        }
        query.where(booleanBuilder);
        List<AisleDTO> result = query.fetch();
        return result;
    }
}
