package com.monopoco.supplier.client;

import com.monopoco.supplier.response.CommonResponse;
import com.monopoco.supplier.service.dto.ProductDTO;
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
 * Package: com.monopoco.supplier.client
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 20:13
 */

@FeignClient(name = "warehouseClient", url = "${productURL}")
public interface ProductClient {

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductDTO>> getProductById(@PathVariable UUID id,
                                                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}
