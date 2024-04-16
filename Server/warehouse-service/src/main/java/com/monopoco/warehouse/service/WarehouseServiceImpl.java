package com.monopoco.warehouse.service;

import com.monopoco.warehouse.entity.WarehouseEntity;
import com.monopoco.warehouse.entity.WarehouseType;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.repository.WarehouseEntityRepository;
import com.monopoco.warehouse.repository.WarehouseRepositoryDSL;
import com.monopoco.warehouse.repository.WarehouseTypeRepository;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
import com.monopoco.warehouse.response.CommonResponse;
import com.monopoco.warehouse.response.PageResponse;
import com.monopoco.warehouse.response.model.DropDown;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.monopoco.warehouse.response.model.WarehouseTypeDTO;
import com.monopoco.warehouse.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public CommonResponse<WarehouseDTO> createNewWarehouse(WarehouseRequest request) {
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
            );
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
                    );
        }
    }

    @Override
    public CommonResponse<WarehouseTypeDTO> createNewType(WarehouseTypeRequest request) {
        WarehouseType warehouseType = warehouseTypeRepository.findByIsDeletedIsFalseAndWarehouseTypeName(
                request.getWarehouseTypeName()
        ).orElse(null);
        if (warehouseType != null) {
            return new CommonResponse<>().isExisted("This warehouse type is existed");
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
}
