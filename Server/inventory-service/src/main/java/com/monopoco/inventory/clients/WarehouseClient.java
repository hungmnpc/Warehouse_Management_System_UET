package com.monopoco.inventory.clients;

import com.monopoco.inventory.response.CommonResponse;
import com.monopoco.inventory.response.model.WarehouseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

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
    @Headers("Authorization: Bearer hungdzqua")
    public ResponseEntity<CommonResponse<WarehouseDTO>> getWarehouse(@PathVariable UUID id,
                                                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}