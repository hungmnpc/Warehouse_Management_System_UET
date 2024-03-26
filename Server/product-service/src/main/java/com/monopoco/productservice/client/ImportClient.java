package com.monopoco.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by: hungdinh
 * Date: 04/03/2024
 * Project: Server
 */

@FeignClient(name = "import-service", url = "${application.config.imports-url.host}")
public interface ImportClient {

    @GetMapping("")
    String test();
}