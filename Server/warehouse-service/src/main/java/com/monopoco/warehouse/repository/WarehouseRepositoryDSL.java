package com.monopoco.warehouse.repository;

import com.monopoco.common.model.PageResponse;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseRepositoryDSL {

    public PageResponse<List<WarehouseDTO>> searchOrder(WarehouseFilter filter, Pageable pageable);
}
