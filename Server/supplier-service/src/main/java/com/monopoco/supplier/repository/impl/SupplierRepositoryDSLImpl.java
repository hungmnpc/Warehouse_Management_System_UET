package com.monopoco.supplier.repository.impl;

import com.monopoco.supplier.entity.QSupplier;
import com.monopoco.supplier.repository.SupplierRepositoryDSL;
import com.monopoco.supplier.response.PageResponse;
import com.monopoco.supplier.service.dto.QSupplierDTO;
import com.monopoco.supplier.service.dto.SupplierDTO;
import com.monopoco.supplier.service.filter.SupplierFilter;
import com.querydsl.core.BooleanBuilder;
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
 * Package: com.monopoco.supplier.repository.impl
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 22:43
 */
@Repository
@Transactional
public class SupplierRepositoryDSLImpl implements SupplierRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QSupplier supplier = QSupplier.supplier;
    @Override
    public PageResponse<List<SupplierDTO>> searchOrder(SupplierFilter filter, Pageable pageable) {
        JPAQuery<SupplierDTO> query = new JPAQuery<>(entityManager)
                .select(new QSupplierDTO(
                        supplier.createdBy,
                        supplier.createdDate,
                        supplier.lastModifiedBy,
                        supplier.lastModifiedDate,
                        supplier.isDeleted,
                        supplier.id,
                        supplier.supplierName,
                        supplier.supplierNumber,
                        supplier.supplierAddress1,
                        supplier.supplierAddress2
                ) )
                .from(supplier)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(supplier.isDeleted.isFalse());
        if (filter != null) {
            if (!StringUtils.isEmpty(filter.getSupplierName())) {
                booleanBuilder.and(supplier.supplierName.containsIgnoreCase(filter.getSupplierName()));
            }
            if (!StringUtils.isEmpty(filter.getSupplierNumber())) {
                booleanBuilder.and(supplier.supplierNumber.containsIgnoreCase(filter.getSupplierNumber()));
            }
        }
        query.where(booleanBuilder);
        List<SupplierDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<SupplierDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
