package com.monopoco.productservice.controller;

import com.monopoco.productservice.client.CommonClient;
import com.monopoco.productservice.client.ImportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: hungdinh
 * Date: 04/03/2024
 * Project: Server
 */

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ImportClient client;

    @Autowired
    private CommonClient commonClient;


    @GetMapping("")
    ResponseEntity<?> test() {
        return ResponseEntity.ok(commonClient.getRandomUUID());
    }
}