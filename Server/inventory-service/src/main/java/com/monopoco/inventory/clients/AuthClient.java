package com.monopoco.inventory.clients;

import com.monopoco.inventory.response.CommonResponse;
import com.monopoco.inventory.response.model.UserDTO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.config.clients
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:44
 */

@FeignClient(name = "authClient", url = "${authURL}")
public interface AuthClient {

    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse<UserDTO>> getUser(@PathVariable UUID id,
                                                           @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token);
}