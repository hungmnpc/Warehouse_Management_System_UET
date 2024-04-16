package com.monopoco.warehouse.service;

import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
import com.monopoco.warehouse.response.CommonResponse;
import com.monopoco.warehouse.response.PageResponse;
import com.monopoco.warehouse.response.model.DropDown;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.monopoco.warehouse.response.model.WarehouseTypeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    public CommonResponse<WarehouseDTO> createNewWarehouse(WarehouseRequest request);

    public CommonResponse<WarehouseTypeDTO> createNewType(WarehouseTypeRequest request);

    public CommonResponse<WarehouseDTO> getWarehouseById(UUID id);

    public CommonResponse<PageResponse<?>> getAllWarehouse(WarehouseFilter filter, Pageable pageable);

    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getDropDownType();

    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getDropDownWarehouse();

    public CommonResponse<String> getDescriptionType(UUID id);

    public CommonResponse<String> deleteWarehouse(UUID id);
}
