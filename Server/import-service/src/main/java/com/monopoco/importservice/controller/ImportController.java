package com.monopoco.importservice.controller;

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
@RequestMapping("/import")
public class ImportController {

    @GetMapping("")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello From Import Service");
    }

}