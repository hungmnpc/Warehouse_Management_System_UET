package com.monopoco.purchaseorder.client;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.purchaseorder.client.dto.SupplierDTO;
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

@FeignClient(name = "supplierClient", url = "${SupplierURL}")
public interface SupplierClient {

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SupplierDTO>> getSupplierById(@PathVariable UUID id,
                                                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}
