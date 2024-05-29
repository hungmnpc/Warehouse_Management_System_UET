package com.monopoco.warehouse.service;

import com.monopoco.common.factory.HistoryFactory;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.purchaseorder.CountGoodsReceivedWarehouse;
import com.monopoco.common.model.purchaseorder.CountPurchaseOrderWarehouse;
import com.monopoco.common.model.warehouse.area.*;
import com.monopoco.warehouse.entity.*;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.repository.*;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
import com.monopoco.warehouse.response.model.DropDown;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.monopoco.warehouse.response.model.WarehouseTypeDTO;
import com.monopoco.warehouse.util.CommonUtil;
import com.monopoco.warehouse.util.PrincipalUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.service
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:47
 */

@Service
@Slf4j
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseEntityRepository warehouseEntityRepository;

    @Autowired
    private WarehouseTypeRepository warehouseTypeRepository;

    @Autowired
    private WarehouseRepositoryDSL warehouseRepositoryDSL;

    @Autowired
    private AreaWarehouseRepository areaWarehouseRepository;

    @Autowired
    private AreaGroupRepository areaGroupRepository;

    @Autowired
    private RackStorageRepository rackStorageRepository;

    @Autowired
    private FloorStorageRepository floorStorageRepository;

    @Autowired
    private LevelRackStorageRepository levelRackStorageRepository;

    @Autowired
    private BinStorageRepository binStorageRepository;

    @Autowired
    private AreaWarehouseRepositoryDSL areaWarehouseRepositoryDSL;

    @Autowired
    private AisleRepository aisleRepository;

    @Autowired
    private BinConfigurationRepository binConfigurationRepository;

    @Override
    public CommonResponse<WarehouseDTO> createNewWarehouse(WarehouseRequest request) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        WarehouseEntity warehouseEntityCheck = warehouseEntityRepository.findByIsDeletedIsFalseAndWarehouseName(
                request.getWarehouseName()
        ).orElse(null);
        if (warehouseEntityCheck != null) {
            return new CommonResponse<>().badRequest("Warehouse name is existed");
        }
        WarehouseType type = warehouseTypeRepository.findByIsDeletedIsFalseAndWarehouseTypeName(request.getWarehouseType().getValue()).orElse(null);
        if (type != null) {
            WarehouseEntity warehouseEntity = WarehouseEntity.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .warehouseName(request.getWarehouseName())
                    .warehouseNameAcronym(request.getWarehouseNameAcronym())
                    .location(request.getLocation())
                    .warehouseTypeId(type.getId())
                    .provinceId(request.getProvinceId())
                    .provinceName(request.getProvinceName())
                    .districtId(request.getDistrictId())
                    .districtName(request.getDistrictName())
                    .wardId(request.getWardId())
                    .wardName(request.getWardName())
                    .build();
            warehouseEntityRepository.save(warehouseEntity);

            return new CommonResponse<>().success().data(WarehouseDTO.builder()
                    .id(warehouseEntity.getId())
                    .warehouseName(warehouseEntity.getWarehouseName())
                    .warehouseNameAcronym(warehouseEntity.getWarehouseNameAcronym())
                    .location(warehouseEntity.getLocation())
                    .provinceId(request.getProvinceId())
                    .provinceName(request.getProvinceName())
                    .districtId(request.getDistrictId())
                    .districtName(request.getDistrictName())
                    .wardId(request.getWardId())
                    .wardName(request.getWardName())
                    .type(type.getWarehouseTypeName())
                    .build()
            ).history(HistoryFactory.createHistoryEventPOST(
                principalUser.getUserId(), principalUser.getUsername(),
                    warehouseEntity.getId(), "warehouse",
                    String.format("Created warehouse: %s", warehouseEntity.getWarehouseName())
            ));
        } else {
            CommonResponse<WarehouseTypeDTO> newTypeResponse = createNewType(
                    WarehouseTypeRequest.builder()
                            .warehouseTypeName(request.getWarehouseType().getValue())
                            .build()
            );
            WarehouseTypeDTO newType = newTypeResponse.getData();
            WarehouseEntity warehouseEntity = WarehouseEntity.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .warehouseName(request.getWarehouseName())
                    .warehouseNameAcronym(request.getWarehouseNameAcronym())
                    .location(request.getLocation())
                    .warehouseTypeId(newType.getId())
                    .provinceId(request.getProvinceId())
                    .provinceName(request.getProvinceName())
                    .districtId(request.getDistrictId())
                    .districtName(request.getDistrictName())
                    .wardId(request.getWardId())
                    .wardName(request.getWardName())
                    .build();
            warehouseEntityRepository.save(warehouseEntity);
            return new CommonResponse<>().success().data(WarehouseDTO.builder()
                            .id(warehouseEntity.getId())
                            .warehouseName(warehouseEntity.getWarehouseName())
                            .warehouseNameAcronym(warehouseEntity.getWarehouseNameAcronym())
                            .location(warehouseEntity.getLocation())
                            .type(newType.getWarehouseTypeName())
                            .provinceId(request.getProvinceId())
                            .provinceName(request.getProvinceName())
                            .districtId(request.getDistrictId())
                            .districtName(request.getDistrictName())
                            .wardId(request.getWardId())
                            .wardName(request.getWardName())
                            .build()
                    ).history(HistoryFactory.createHistoryEventPOST(
                    principalUser.getUserId(), principalUser.getUsername(),
                    warehouseEntity.getId(), "warehouse",
                    String.format("Created warehouse: %s", warehouseEntity.getWarehouseName())
            ));
        }
    }

    @Override
    public CommonResponse<WarehouseTypeDTO> createNewType(WarehouseTypeRequest request) {
        WarehouseType warehouseType = warehouseTypeRepository.findByIsDeletedIsFalseAndWarehouseTypeName(
                request.getWarehouseTypeName()
        ).orElse(null);
        if (warehouseType != null) {
            return new CommonResponse<>().badRequest("This warehouse type is existed");
        } else {
            WarehouseType newType = WarehouseType.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .warehouseTypeName(request.getWarehouseTypeName())
                    .warehouseTypeDescription(request.getDescription() == null ? "No description" :
                            request.getDescription())
                    .build();

            WarehouseType savedType = warehouseTypeRepository.save(newType);
            return new CommonResponse<>().success().data(WarehouseTypeDTO.builder()
                    .id(savedType.getId())
                    .warehouseTypeName(savedType.getWarehouseTypeName())
                    .description(savedType.getWarehouseTypeDescription())
                    .build());
        }
    }

    @Override
    public CommonResponse<PageResponse<?>> getAllWarehouse(WarehouseFilter filter, Pageable pageable) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        if (principalUser.getWarehouseId() != null) {
            filter.setWarehouseId(principalUser.getWarehouseId());
        }
        PageResponse<List<WarehouseDTO>> getListWarehouse = warehouseRepositoryDSL.searchOrder(filter, pageable);
        return new CommonResponse<>().success().data(getListWarehouse);
    }

    @Override
    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getDropDownType() {
        List<WarehouseType> warehouseTypeList = warehouseTypeRepository.findAllByIsDeletedIsFalse();
        List<DropDown<UUID, String>> typeDropDown = warehouseTypeList.stream()
                .map(
                        (warehouseType ->
                                new DropDown<UUID, String>(warehouseType.getId(),
                                        warehouseType.getWarehouseTypeName())))
                .toList();
        return new CommonResponse<>().success().data(new PageResponse<List<DropDown<UUID, String>>>().data(typeDropDown)
                .dataCount(typeDropDown.size())
                .pageNumber(0)
                .pageSize(0));
    }

    @Override
    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getDropDownWarehouse() {
        List<WarehouseEntity> warehouseList = warehouseEntityRepository.findAllByIsDeletedIsFalse();
        List<DropDown<UUID, String>> warehouseDropDown = warehouseList.stream()
                .map(
                        (warehouse ->
                                new DropDown<UUID, String>(warehouse.getId(),
                                        warehouse.getWarehouseName())))
                .toList();
        return new CommonResponse<>().success().data(new PageResponse<List<DropDown<UUID, String>>>().data(warehouseDropDown)
                .dataCount(warehouseDropDown.size())
                .pageNumber(0)
                .pageSize(0));
    }

    @Override
    public CommonResponse<String> getDescriptionType(UUID id) {
        WarehouseType warehouseType = warehouseTypeRepository.findByIsDeletedIsFalseAndId(id).orElse(
                new WarehouseType(null, "Not Found", "No Info")
        );
        return new CommonResponse<>().success().data(warehouseType.getWarehouseTypeDescription());
    }

    @Override
    public CommonResponse<String> deleteWarehouse(UUID id) {
        WarehouseEntity warehouseEntity = warehouseEntityRepository.findByIsDeletedIsFalseAndId(id).orElse(null);
        if (warehouseEntity != null) {
            warehouseEntity.setIsDeleted(true);
            return new CommonResponse<>().success("Delete Warehouse Successful");
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<WarehouseDTO> getWarehouseById(UUID id) {
        WarehouseEntity warehouseEntity = warehouseEntityRepository.findByIsDeletedIsFalseAndId(id).orElse(null);

        if (warehouseEntity != null) {
            WarehouseType warehouseType = warehouseTypeRepository.findByIsDeletedIsFalseAndId(warehouseEntity.getWarehouseTypeId()).orElse(
                    WarehouseType.builder()
                            .id(CommonUtil.generateRandomUUID())
                            .warehouseTypeName("Not Found")
                            .warehouseTypeDescription("Not Found")
                            .build()
            );
            return new CommonResponse<>().success().data(
                    WarehouseDTO.builder()
                            .id(warehouseEntity.getId())
                            .warehouseName(warehouseEntity.getWarehouseName())
                            .warehouseNameAcronym(warehouseEntity.getWarehouseNameAcronym())
                            .type(warehouseType.getWarehouseTypeName())
                            .location(warehouseEntity.getLocation())
                            .provinceId(warehouseEntity.getProvinceId())
                            .provinceName(warehouseEntity.getProvinceName())
                            .districtId(warehouseEntity.getDistrictId())
                            .districtName(warehouseEntity.getDistrictName())
                            .wardId(warehouseEntity.getWardId())
                            .wardName(warehouseEntity.getWardName())
                            .createdDate(warehouseEntity.getCreatedDate())
                            .createdBy(warehouseEntity.getCreatedBy())
                            .build()
            );
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<CountPurchaseOrderWarehouse> countPoWH(UUID warehouseId) {
        long countAll = warehouseEntityRepository.countAllPurchaseOrderOfWarehouse(warehouseId);
        long countNeedHandle = warehouseEntityRepository.countNeedHandlePurchaseOrderOfWarehouse(warehouseId);
        return new CommonResponse<>().success()
                .data(new CountPurchaseOrderWarehouse(
                        warehouseId, countAll, countNeedHandle
                ));
    }

    @Override
    public CommonResponse<CountGoodsReceivedWarehouse> countGCWH(UUID warehouseId) {
        long countAll = warehouseEntityRepository.countGoodReceived(warehouseId);
        long countNeedHandle = warehouseEntityRepository.countGoodReceivedNeedHandle(
                warehouseId);
        return new CommonResponse<>().success()
                .data(new CountGoodsReceivedWarehouse(
                        warehouseId, countAll, countNeedHandle
                ));
    }

    @Override
    public CommonResponse<?> creatArea(UUID warehouseId, CreateArea createArea) {
        WarehouseEntity warehouseEntity = warehouseEntityRepository.findByIsDeletedIsFalseAndId(warehouseId).orElse(null);
        if (warehouseEntity != null) {
            AreaGroup areaGroup = areaGroupRepository.findById(createArea.getAreaGroupId()).orElse(null);

            if (areaGroup == null) {
                areaGroup = new AreaGroup(
                        CommonUtil.generateRandomUUID(),
                        createArea.getAreaGroupName(),
                        createArea.getAreaGroupName()
                );
                areaGroupRepository.save(areaGroup);
            }
            Integer lastOrderWarehousePrefix = areaWarehouseRepository.getLastOrderAreaPrefix(createArea.getAreaPrefix());
            if (lastOrderWarehousePrefix == null) {
                lastOrderWarehousePrefix = 0;
            } else {
                lastOrderWarehousePrefix += 1;
            }
            AreaWarehouse areaWarehouse = new AreaWarehouse(
                    CommonUtil.generateRandomUUID(),
                    createArea.getAreaName(),
                    createArea.getAreaPrefix(),
                    lastOrderWarehousePrefix,
                    warehouseId,
                    warehouseEntity.getWarehouseName(),
                    areaGroup.getId()
            );
            areaWarehouseRepository.save(areaWarehouse);
            return new CommonResponse<>().success().data(areaWarehouse.getId())
                    .history(HistoryFactory.createHistoryEventPOST(
                            CommonUtil.getRecentUser().getUserId(),
                            CommonUtil.getRecentUser().getUsername(),
                            warehouseId,
                            "warehouse",
                            String.format("Create area: %s", createArea.getAreaName())
                    ));
        } else {
            return new CommonResponse<>().notFound().message("Not found the warehouse");
        }
    }

    @Override
    public CommonResponse<?> createAreaGroup(CreateAreaGroup createAreaGroup) {
        AreaGroup areaGroup = areaGroupRepository.findByAreaGroupId(createAreaGroup.getAreaGroupId()).orElse(null);
        if (areaGroup != null) {
            return new CommonResponse<>().badRequest("Area group is existed");
        } else {
            areaGroup = new AreaGroup(
                    CommonUtil.generateRandomUUID(),
                    createAreaGroup.getAreaGroupId(),
                    createAreaGroup.getAreaGroupName()
            );
            areaGroupRepository.save(areaGroup);
            return new CommonResponse<>().success().data(areaGroup.getId());
        }
    }

    @Override
    public CommonResponse<?> createRackStorage(UUID areaId, UUID warehouseId, UUID aisleId, String aisleName, String areaName, CreateRackStorage createRackStorage) {
            for (int i = 1; i <= createRackStorage.getNumberOfRack(); i++) {
                RackStorage rackStorage = new RackStorage(
                        CommonUtil.generateRandomUUID(),
                        String.format("%s%02d", createRackStorage.getRackPrefix(), i),
                        aisleId
                );
                for (int j = 0; j < createRackStorage.getNumberOfLevelEachRack(); j++) {
                    LevelRackStorage levelRackStorage = new LevelRackStorage(
                            CommonUtil.generateRandomUUID(),
                            rackStorage.getRackId(),
                            String.format("L%d", j),
                            j
                    );
                    for (int k = 0; k < createRackStorage.getNumberOfBinEachLevel(); k++) {
                        BinStorage binStorage = BinStorage.builder()
                                        .binId(CommonUtil.generateRandomUUID())
                                        .binName(String.format("B%02d", k))
                                        .warehouseId(warehouseId)
                                .areaId(areaId)
                                        .barcode(String.format("%s-%s-%s-%s-%s",areaName,
                                                aisleName, rackStorage.getRackName(),
                                                levelRackStorage.getLevelName(), String.format("B%02d", k)))
                                        .rackLevelStorageId(levelRackStorage.getLevelRackId())
                                .disable(false)
                                .occupied(false)
                                    .build();
                        binStorageRepository.save(binStorage);
                    }
                    levelRackStorageRepository.save(levelRackStorage);
                }
                rackStorageRepository.save(rackStorage);
            }
            return new CommonResponse<>().success().data(aisleId);
    }

    @Override
    public CommonResponse<?> createFloorStorage(UUID areaId, UUID warehouseId,UUID aisleId, String aisleName, String areaName, CreateFloorStorage createFloorStorage) {
            for (int i = 1; i <= createFloorStorage.getNumberOfFloorStorage() ; i++) {
                FloorStorage floorStorage = new FloorStorage(
                        CommonUtil.generateRandomUUID(),
                            String.format("%s%02d", createFloorStorage.getFloorStoragePrefix(), i),
                            aisleId
                        );
                for (int k = 0; k < createFloorStorage.getNumberOfBin(); k++) {
                    BinStorage binStorage = BinStorage.builder()
                            .binId(CommonUtil.generateRandomUUID())
                            .binName(String.format("B%02d", k))
                            .barcode(String.format("%s-%s-%s-%s",areaName,
                                    aisleName, floorStorage.getFloorName(), String.format("B%02d", k)))
                            .floorStorageId(floorStorage.getFloorId())
                            .warehouseId(warehouseId)
                            .areaId(areaId)
                            .disable(false)
                            .occupied(false)
                            .isStorageMultipleProduct(createFloorStorage.getMultipleProduct())
                            .build();
                    binStorageRepository.save(binStorage);
                }
                floorStorageRepository.save(floorStorage);
            }
            return new CommonResponse<>().success().data(aisleId);
    }

    @Override
    public CommonResponse<?> getAllAreaInWarehouse(UUID warehouseId) {
        List<AreaWarehouseDTO> areaWarehouseDTOS = areaWarehouseRepositoryDSL.areaWarehouse(warehouseId);
        return new CommonResponse<>().success().data(areaWarehouseDTOS);
    }

    @Override
    public CommonResponse<?> createAisleWarehouse(UUID areaId, CreateAisleWarehouse createAisleWarehouse) {
        AreaWarehouse areaWarehouse = areaWarehouseRepository.findById(areaId).orElse(null);
        if (areaWarehouse != null) {

            Aisle aisle = aisleRepository.findByAisleNameAndAreaId(
                    createAisleWarehouse.getAisleName(), areaId
            ).orElse(null);
            if (aisle != null) {
                return new CommonResponse<>().badRequest("Aisle name is existed in this warehouse.");
            }
             aisle = new Aisle(
                    CommonUtil.generateRandomUUID(),
                    createAisleWarehouse.getAisleName(),
                    createAisleWarehouse.getLocationType(),
                    areaId
            );
            aisleRepository.save(aisle);

            HistoryEvent historyEvent = HistoryFactory.createHistoryEventPOST(
                    CommonUtil.getRecentUser().getUserId(),
                    CommonUtil.getRecentUser().getUsername(),
                    areaWarehouse.getWarehouseId(),
                    "warehouse",
                    String.format("Create aisle `%s` in area `%s`", createAisleWarehouse.getAisleName(), areaWarehouse.getAreaName())
            );


            if (createAisleWarehouse.getLocationType().equals(LocationType.FLOOR)) {
                // Create Floor storage
                 CommonResponse<?> response = createFloorStorage( areaWarehouse.getId() ,areaWarehouse.getWarehouseId(),aisle.getAisleId(),aisle.getAisleName(),
                         String.format("%s%d",areaWarehouse.getAreaPrefix(), areaWarehouse.getOrderArea()) ,
                         createAisleWarehouse.getCreateFloorStorage());
                 if (response.isSuccess()) {
                     return new CommonResponse<>().success().data(aisle.getAisleId()).history(historyEvent);
                 } else {
                     return new CommonResponse<>().badRequest();
                 }

            } else if (createAisleWarehouse.getLocationType().equals(LocationType.RACK)) {
                //Create Rack storage
                CommonResponse<?> response = createRackStorage(areaWarehouse.getId() ,areaWarehouse.getWarehouseId() ,aisle.getAisleId(),
                        aisle.getAisleName(), String.format("%s%d", areaWarehouse.getAreaPrefix(), areaWarehouse.getOrderArea()),
                        createAisleWarehouse.getCreateRackStorage());
                if (response.isSuccess()) {
                    return new CommonResponse<>().success().data(aisle.getAisleId()).history(historyEvent);
                } else {
                    return new CommonResponse<>().badRequest();
                }
            } else {
                return new CommonResponse<>().badRequest("Invalid location type");
            }
        } else {
            return new CommonResponse<>().notFound("Not founded area");
        }
    }

    @Override
    public CommonResponse<?> getAisleInArea(UUID areaId) {
        List<AisleDTO> aisleDTOS = areaWarehouseRepositoryDSL.getAllAisleInArea(
                areaId
        );
        return new CommonResponse<>().success().data(aisleDTOS);
    }

    @Override
    public CommonResponse<?> getBinsInAisleLocation(UUID aisleId) {
        Aisle aisle = aisleRepository.findById(aisleId).orElse(null);
        if (aisle != null) {
            Optional<AreaWarehouse> areaWarehouse = areaWarehouseRepository.findById(aisle.getAreaId());
            if (areaWarehouse.isEmpty()) {
                return new CommonResponse<>().notFound();
            }
            List<RackStorage> rackStorages = rackStorageRepository.getAllByAisleIdOrderByRackName(aisleId);
            List<RackLocation> rackLocations = rackStorages.stream().map(rackStorage -> {
                List<LevelRackStorage> levelRackStorages = levelRackStorageRepository.findAllByRackStorageIdOrderByLevel(
                        rackStorage.getRackId()
                );
                List<LevelRackLocation> levelRackLocations = levelRackStorages.stream().map(levelRackStorage -> {
                    List<BinStorage> binStorages = binStorageRepository.findAllByRackLevelStorageIdOrderByBinName(levelRackStorage.getLevelRackId());
                    List<BinLocation> binLocations = binStorages.stream().map(binStorage -> new BinLocation(
                            binStorage.getBinId(),
                            String.format("%s-%s-%s-%s-%s",areaWarehouse.get().getAreaPrefix(), aisle.getAisleName(), rackStorage.getRackName(), levelRackStorage.getLevelName(), binStorage.getBinName()),
                            binStorage.getDisable(),
                            binStorage.getOccupied(),
                            binStorage.getIsStorageMultipleProduct(),
                            binStorage.getBarcode()
                    )).toList();
                    return new LevelRackLocation(
                            levelRackStorage.getLevelRackId(),
                            levelRackStorage.getLevelName(),
                            levelRackStorage.getLevel(),
                            binLocations
                    );
                }).toList();
                return new RackLocation(rackStorage.getRackId(), rackStorage.getRackName(), levelRackLocations);
            }).toList();
            return new CommonResponse<>().success().data(rackLocations);
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> getBinLocationByBarCode(String barcode, UUID warehouseId) {
        List<String> splitBarcode = Arrays.stream(barcode.split("-")).toList();
        BinStorage binStorage = binStorageRepository.findByBarcodeAndWarehouseId(barcode, warehouseId).orElse(null);
        if (binStorage == null) {
            return new CommonResponse<>().notFound();
        }
        if (splitBarcode.size() == 5) {
            //Rack Barcode
            AreaWarehouseDTO area = getAreaInBarcode(splitBarcode.get(0), warehouseId, false);
            if (area == null) {
                return new CommonResponse<>().notFound("Not founded area");
            }
            AisleDTO aisleDTO = getAisleInArea(splitBarcode.get(1), area.getId());
            if (aisleDTO == null) {
                return new CommonResponse<>().notFound("Not founded aisle");
            }
            RackLocation rackLocation = getRackInAisle(splitBarcode.get(2), aisleDTO.getAisleId());
            if (rackLocation == null) {
                return new CommonResponse<>().notFound("Not founded rack location");
            }
            LevelRackLocation levelRackLocation = getLevelRack(splitBarcode.get(3), rackLocation.getRackId());
            if (levelRackLocation == null) {
                return new CommonResponse<>().notFound("Not founded level rack location");
            }

            BinConfiguration binConfiguration = binConfigurationRepository.findByBinId(binStorage.getBinId()).orElse(
                    null
            );
            BinConfigurationDTO binConfigurationDTO;
            if (binConfiguration == null) {
                binConfigurationDTO = BinConfigurationDTO.builder().build();
            } else {
                binConfigurationDTO = new BinConfigurationDTO(
                        binConfiguration.getBinDescription(),
                        binConfiguration.getMaxStorage(),
                        binConfiguration.getUnitStorage(),
                        binConfiguration.getOnlyProductId(),
                        binConfiguration.getOnlyProductName()
                );
            }


            BinLocationDetail binLocationDetail = new BinLocationDetail(
                    area, aisleDTO, rackLocation, levelRackLocation,
                    new BinLocation(binStorage.getBinId(), binStorage.getBinName(), binStorage.getDisable(),
                            binStorage.getOccupied(), binStorage.getIsStorageMultipleProduct(),
                            binStorage.getBarcode()), binConfigurationDTO);
            return new CommonResponse<>().success().data(binLocationDetail);
        } else if (splitBarcode.size() == 4) {
            //Floor Barcode
            return new CommonResponse<>().success().data(binStorage.getBarcode());
        } else {
            return new CommonResponse<>().badRequest("Invalid barcode");
        }
    }

    private AreaWarehouseDTO getAreaInBarcode(String prefix, UUID warehouseId, Boolean isDetail) {
        String prefixLetter = prefix.replaceAll("[0-9]", "");
        Integer prefixDigital = Integer.valueOf(prefix.replaceAll("[^0-9]", ""));
        AreaWarehouse areaWarehouse = areaWarehouseRepository.findByAreaPrefixAndOrderAreaAndWarehouseId(prefixLetter, prefixDigital, warehouseId).orElse(null);

        if (areaWarehouse != null) {
            AreaGroup areaGroup = areaGroupRepository.findById(areaWarehouse.getAreaGroupId()).orElse(
                    AreaGroup.builder()
                            .areaGroupId("Undefined")
                            .areaGroupName("Undefined").build()
            );
            return AreaWarehouseDTO.builder()
                    .id(areaWarehouse.getId())
                    .areaGroupName(areaGroup.getAreaGroupId())
                    .warehouseId(areaWarehouse.getWarehouseId())
                    .warehouseName(areaWarehouse.getWarehouseName())
                    .areaGroupName(areaWarehouse.getWarehouseName())
                    .areaGroupId(areaGroup.getId())
                    .areaName(areaWarehouse.getAreaName())
                    .areaPrefix(String.format("%s%d", areaWarehouse.getAreaPrefix(), areaWarehouse.getOrderArea()))
                    .build();
        }
        return null;
    }

    private AisleDTO getAisleInArea(String prefix, UUID areaId) {
        Aisle aisle = aisleRepository.findByAisleNameAndAreaId(
                prefix, areaId
        ).orElse(null);
        if (aisle != null) {
            return AisleDTO.builder()
                    .aisleName(aisle.getAisleName())
                    .aisleId(aisle.getAisleId())
                    .locationType(aisle.getLocationType())
                    .build();
        }
        return null;
    }

    private RackLocation getRackInAisle(String rackName, UUID aisleId) {
        RackStorage rackStorage = rackStorageRepository.findByRackNameAndAisleId(
                rackName, aisleId
        ).orElse(null);
        if (rackStorage != null) {
            return RackLocation.builder()
                    .rackId(rackStorage.getRackId())
                    .rackName(rackStorage.getRackName())
                    .build();
        }
        return null;
    }

    private LevelRackLocation getLevelRack(String levelName, UUID rackId) {
        LevelRackStorage levelRackStorage = levelRackStorageRepository.findByLevelNameAndRackStorageId(
                levelName, rackId
        ).orElse(null);
        if (levelRackStorage != null) {
            return LevelRackLocation.builder()
                    .level(levelRackStorage.getLevel())
                    .levelRackId(levelRackStorage.getLevelRackId())
                    .levelName(levelRackStorage.getLevelName())
                    .build();
        }
        return null;
    }

    @Override
    public CommonResponse<?> configBin(BinConfigurationDTO binConfigurationDTO, UUID binId) {
        BinStorage binStorage = binStorageRepository.findById(binId).orElse(null);
        if (binStorage == null) {
            return new CommonResponse<>().notFound("Not found bin location");
        }
        BinConfiguration binConfiguration = binConfigurationRepository.findByBinId(binId).orElse(
                null
        );
        if (binConfiguration == null) {
            binConfiguration = new BinConfiguration(
                    CommonUtil.generateRandomUUID(),
                    binId,
                    binConfigurationDTO.getBinDescription(),
                    binConfigurationDTO.getMaxStorage(),
                    binConfigurationDTO.getUnitStorage(),
                    binConfigurationDTO.getOnlyProductId(),
                    binConfigurationDTO.getOnlyProductName()
            );
        } else {
            binConfiguration.updateFromDTO(binConfigurationDTO);
        }
        log.error("{}", binConfiguration.toString());
        binConfigurationRepository.save(binConfiguration);
        if (binConfigurationDTO.getOnlyProductId() != null) {
            binStorage.setIsStorageMultipleProduct(false);
        } else {
            binStorage.setIsStorageMultipleProduct(true);
        }
        return new CommonResponse<>().success().data(binConfiguration.getId());
    }

    @Override
    public CommonResponse<?> getAreaGroups() {
        List<AreaGroup> areaGroups = areaGroupRepository.findAll();
        List<DropDown<UUID, String>> response = areaGroups.stream().map(
                areaGroup -> new DropDown<UUID, String>(areaGroup.getId(),areaGroup.getAreaGroupId())
        ).toList();
        return new CommonResponse<>().success().data(response);
    }

    @Override
    public CommonResponse<?> updateOccupiedBin(Boolean occupied, UUID binId) {
        BinStorage binStorage = binStorageRepository.findById(binId).orElse(null);
        if (binStorage != null) {
            binStorage.setOccupied(occupied);
        }
        return new CommonResponse<>().success().data(binId);
    }
}
