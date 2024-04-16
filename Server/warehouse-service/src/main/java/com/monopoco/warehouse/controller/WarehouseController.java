package com.monopoco.warehouse.controller;

import com.monopoco.warehouse.clients.HistoryClient;
import com.monopoco.warehouse.clients.dto.HistoryDTO;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.request.WarehouseRequest;
import com.monopoco.warehouse.request.WarehouseTypeRequest;
import com.monopoco.warehouse.response.CommonResponse;
import com.monopoco.warehouse.response.model.UserDTO;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import com.monopoco.warehouse.service.WarehouseService;
import com.monopoco.warehouse.util.CommonUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.POST;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private HistoryClient historyClient;

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
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UUID userID = (UUID) ((Map<String, Object>) auth.getPrincipal()).get("id");
                historyClient.pushHistory(HistoryDTO.builder()
                        .title("Create new warehouse")
                        .type("create".toUpperCase())
                        .agentId(response.getData().getId())
                        .user(
                                UserDTO.builder().id(userID).build()
                        ).agentType("warehouse")
                        .description(new HashMap<>() {{
                            put("description", String.format("Warehouse: %s was created.", request.getWarehouseName()));
                        }}).build());
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
}
