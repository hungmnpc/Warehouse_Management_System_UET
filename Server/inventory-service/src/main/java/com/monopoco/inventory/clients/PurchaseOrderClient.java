package com.monopoco.inventory.clients;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDetailDTO;
import com.monopoco.common.model.warehouse.area.BinLocationDetail;
import com.monopoco.inventory.response.model.WarehouseDTO;
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

@FeignClient(name = "purchsaeClient", url = "${purchaseOrderURL}")
public interface PurchaseOrderClient {

    @GetMapping("/{purchaseOrderId}/detail/{productId}")
    public ResponseEntity<CommonResponse<PurchaseOrderDetailDTO>> getDetailItemPo(
            @PathVariable UUID purchaseOrderId,
            @PathVariable UUID productId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
    );
}