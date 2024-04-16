package com.monopoco.warehouse.clients;

import com.monopoco.warehouse.clients.dto.HistoryDTO;
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
@Headers("Authorization: Bearer hungdzqua")
public interface HistoryClient {

    @PostMapping("")
    @Headers("Authorization: Bearer hungdzqua")
    public ResponseEntity<?> pushHistory(@RequestBody HistoryDTO historyDTO);
}
