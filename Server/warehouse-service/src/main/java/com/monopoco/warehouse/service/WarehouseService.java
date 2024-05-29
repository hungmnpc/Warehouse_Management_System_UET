package com.monopoco.warehouse.service;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.purchaseorder.CountGoodsReceivedWarehouse;
import com.monopoco.common.model.purchaseorder.CountPurchaseOrderWarehouse;
import com.monopoco.common.model.warehouse.area.*;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
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

    public CommonResponse<CountPurchaseOrderWarehouse> countPoWH(UUID warehouseId);

    public CommonResponse<CountGoodsReceivedWarehouse> countGCWH(UUID warehouseId);

    public CommonResponse<?> creatArea(UUID warehouseId, CreateArea createArea);

    public CommonResponse<?> createAreaGroup(CreateAreaGroup createAreaGroup);

    public CommonResponse<?> createRackStorage(UUID areaId, UUID warehouseId,UUID aisleId, String aisleName, String areaName, CreateRackStorage createRackStorage);

    public CommonResponse<?> createFloorStorage(UUID areaId, UUID warehouseId, UUID aisleId, String aisleName, String areaName, CreateFloorStorage createFloorStorage);

    public CommonResponse<?> getAllAreaInWarehouse(UUID warehouseId);

    public CommonResponse<?> createAisleWarehouse(UUID areaId, CreateAisleWarehouse createAisleWarehouse);

    public CommonResponse<?> getAisleInArea(UUID areaId);

    public CommonResponse<?> getBinsInAisleLocation(UUID aisleId);

    public CommonResponse<?> getBinLocationByBarCode(String barcode, UUID warehouseId);

    public CommonResponse<?> configBin(BinConfigurationDTO binConfigurationDTO, UUID binId);

    public CommonResponse<?> getAreaGroups();

    public CommonResponse<?> updateOccupiedBin(Boolean occupied, UUID binId);
}
