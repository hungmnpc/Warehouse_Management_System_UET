package com.monopoco.inventory.clients;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Project: Server
 * Package: com.monopoco.inventory.clients
 * Author: hungdq
 * Date: 12/05/2024
 * Time: 22:57
 */

@FeignClient(name = "productClient", url = "${productURL}")
public interface ProductClient {

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<CommonResponse<ProductDTO>> getProductByBarcode(
            @PathVariable String barcode,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}