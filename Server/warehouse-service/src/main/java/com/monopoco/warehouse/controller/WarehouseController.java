package com.monopoco.warehouse.controller;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.warehouse.area.*;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.monopoco.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.controller
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:15
 */

@RestController
@RequestMapping("/warehouses")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;


    @GetMapping("")
    public ResponseEntity<?> getAllWarehouse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "", required = false) String warehouseName,
            @RequestParam(defaultValue = "createdDate", required = false) String orderBy,
            @RequestParam(defaultValue = "desc", required = false) String orderType
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            WarehouseFilter filter = WarehouseFilter.builder()
                    .warehouseName(warehouseName)
                    .orderBy(orderBy)
                    .orderType(orderType)
                    .build();
            CommonResponse<?> response = warehouseService.getAllWarehouse(filter, pageable);
            return ResponseEntity.ok().body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createNewWarehouse(@RequestBody WarehouseRequest request, HttpServletRequest httpServletRequest) {
        try {
            CommonResponse<WarehouseDTO> response = warehouseService.createNewWarehouse(request);
            if (response.isSuccess()) {
                return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI())).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/dropdown")
    public ResponseEntity<?> getDropDownWarehouse() {
        try {
            CommonResponse<?> response = warehouseService.getDropDownWarehouse();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }


    @GetMapping("/dropdown/types")
    public ResponseEntity<?> getDropDownType() {
        try {
            CommonResponse<?> response = warehouseService.getDropDownType();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/types/description/{id}")
    public ResponseEntity<?> getDescriptionType(@PathVariable UUID id) {
        try {
            CommonResponse response = warehouseService.getDescriptionType(id);
            return ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/types")
    public ResponseEntity<?> createNewType(@RequestBody WarehouseTypeRequest request, HttpServletRequest httpServletRequest) {
        try {
            CommonResponse<?> response = warehouseService.createNewType(request);
            if (response.isSuccess()) {
                return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI())).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable UUID id) {
        try {
            CommonResponse<?> response = warehouseService.deleteWarehouse(id);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarehouseById(@PathVariable UUID id) {
        try {
            CommonResponse<?> response = warehouseService.getWarehouseById(id);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{warehouseId}/purchase_orders/count")
    public ResponseEntity<?> getCountPOWH(
            @PathVariable UUID warehouseId
    ) {
        try {
            CommonResponse<?> response = warehouseService.countPoWH(warehouseId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{warehouseId}/goods_received/count")
    public ResponseEntity<?> getCountGRWH(
            @PathVariable UUID warehouseId
    ) {
        try {
            CommonResponse<?> response = warehouseService.countGCWH(warehouseId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/areas/groups")
    public ResponseEntity<?> getAreaGroup() {
        try {
            CommonResponse<?> response = warehouseService.getAreaGroups();
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/{warehouseId}/areas")
    public ResponseEntity<?> createNewAreaInWH(
            @PathVariable UUID warehouseId,
            @RequestBody CreateArea createArea
    ) {
        try {
            CommonResponse<?> response = warehouseService.creatArea(warehouseId, createArea);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/area_groups")
    public ResponseEntity<?> createNewAreaGroup(@RequestBody CreateAreaGroup createAreaGroup) {
        try {
            CommonResponse<?> response = warehouseService.createAreaGroup(createAreaGroup);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }


    @GetMapping("/{warehouseId}/areas")
    public ResponseEntity<?> getAllAreaInWH(
            @PathVariable UUID warehouseId
    ) {
        try {
            CommonResponse<?> response = warehouseService.getAllAreaInWarehouse(warehouseId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/areas/{areaId}/aisles")
    public ResponseEntity<?> createAisleWH (
            @PathVariable UUID areaId,
            @RequestBody CreateAisleWarehouse createAisleWarehouse
            ) {
        try {
            CommonResponse<?> response = warehouseService.createAisleWarehouse(areaId, createAisleWarehouse);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/areas/{areaId}/aisles")
    public ResponseEntity<?> getAislesInArea(
            @PathVariable UUID areaId
    ) {
        try {
            CommonResponse<?> response = warehouseService.getAisleInArea(areaId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/areas/aisles/{aislesId}/bins")
    public ResponseEntity<?> getBinLocation(
            @PathVariable UUID aislesId
    ) {
        try {
            CommonResponse<?> response = warehouseService.getBinsInAisleLocation(aislesId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/bins/barcode/{barcode}")
    public ResponseEntity<?> getBinLocationByBarcode(
            @PathVariable String barcode,
            @RequestParam(required = true) UUID warehouseId
    ) {
        try {
            CommonResponse<?> response = warehouseService.getBinLocationByBarCode(barcode, warehouseId);
            return ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/bins/{binId}/config")
    public ResponseEntity<?> configBin(
            @PathVariable UUID binId,
            @RequestBody BinConfigurationDTO request
    ) {
        try {
            CommonResponse<?> response = warehouseService.configBin(request, binId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PutMapping("/bins/{binId}/occupied")
    public ResponseEntity<?> updateOccupied(@PathVariable UUID binId, @RequestBody Boolean occupied) {
        try {
            CommonResponse<?> response = warehouseService.updateOccupiedBin(occupied, binId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }
}
