package com.monopoco.inventory.clients;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.clients
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 17:35
 */
@FeignClient(name = "historyClient", url = "${historyURL}")
public interface HistoryClient {

    @PostMapping("")
    public ResponseEntity<?> pushHistory(@RequestBody HistoryDTO historyDTO);
}
