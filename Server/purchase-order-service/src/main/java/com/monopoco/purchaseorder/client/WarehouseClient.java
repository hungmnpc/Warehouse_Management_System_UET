package com.monopoco.purchaseorder.client;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.purchaseorder.client.dto.WarehouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.client
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 16:10
 */

@FeignClient(name = "warehouseClient", url = "${WarehouseURL}")
public interface WarehouseClient {

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<WarehouseDTO>> getWarehouseById(@PathVariable UUID id,
                                                                         @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}
