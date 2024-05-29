package com.monopoco.purchaseorder.client;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.user.UserDTO;
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
 * Date: 15/05/2024
 * Time: 23:52
 */

@FeignClient(name = "authClient", url = "${AuthURL}")
public interface UserClient {

    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse<UserDTO>> getUserById(
            @PathVariable UUID id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
    );
}
