package com.monopoco.productservice.client;


import com.monopoco.productservice.model.response.CommonResponse;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@FeignClient(name = "common-service", url = "${application.config.common_service-url.host}")
public interface CommonClient {

    @GetMapping("${application.config.common_service-url.random-UUID}")
    CommonResponse<UUID> getRandomUUID();
}
