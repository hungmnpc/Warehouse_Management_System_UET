package com.monopoco.supplier.repository;

import com.monopoco.supplier.response.PageResponse;
import com.monopoco.supplier.service.dto.SupplierDTO;
import com.monopoco.supplier.service.filter.SupplierFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.supplier.repository
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 22:41
 */
public interface SupplierRepositoryDSL {

    public PageResponse<List<SupplierDTO>> searchOrder(SupplierFilter filter, Pageable pageable);

}