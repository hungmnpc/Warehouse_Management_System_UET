package com.monopoco.common.controller;

import com.monopoco.common.model.response.CommonResponse;
import com.monopoco.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/common-utils")
@CrossOrigin("*")
public class CommonUtilController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/random-UUID")
    public ResponseEntity<CommonResponse<UUID>> getRandomUUID() {
        try {
            CommonResponse<UUID> response = commonService.generateRandomUUID();
            if (response != null && response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(new CommonResponse<>().badRequest());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(new CommonResponse<>().errorCode("500").message("Server xảy ra lỗi"));
        }
    }
}
