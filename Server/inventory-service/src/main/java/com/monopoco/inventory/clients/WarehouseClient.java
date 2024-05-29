package com.monopoco.inventory.clients;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.warehouse.area.BinLocationDetail;
import com.monopoco.inventory.response.model.WarehouseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.config.clients
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:44
 */

@FeignClient(name = "warehouseClient", url = "${warehouseURL}")
public interface WarehouseClient {

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<WarehouseDTO>> getWarehouse(@PathVariable UUID id,
                                                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/bins/barcode/{barcode}")
    public ResponseEntity<CommonResponse<BinLocationDetail>> getBinLocationByBarcode(
            @PathVariable String barcode,
            @RequestParam(required = true) UUID warehouseId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
    );

    @PutMapping("/bins/{binId}/occupied")
    public ResponseEntity<CommonResponse<?>> updateOccupied(
            @PathVariable UUID binId,
            @RequestBody Boolean occupied,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
    );
}