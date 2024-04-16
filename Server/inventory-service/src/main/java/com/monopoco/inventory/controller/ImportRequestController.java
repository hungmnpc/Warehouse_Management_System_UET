package com.monopoco.inventory.controller;

import com.monopoco.inventory.clients.HistoryClient;
import com.monopoco.inventory.clients.HistoryDTO;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.request.ImportRequestBody;
import com.monopoco.inventory.response.CommonResponse;
import com.monopoco.inventory.response.model.UserDTO;
import com.monopoco.inventory.service.ImportRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * Project: Server
 * Package: com.monopoco.inventory.controller
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:27
 */

@RequestMapping("/imports")
@RestController
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ImportRequestController {

    @Autowired
    private ImportRequestService importRequestService;

    @Autowired
    private HistoryClient historyClient;
    @PostMapping("")
    public ResponseEntity<?> pushNewImportRequest(@RequestBody ImportRequestBody requestBody) {
        try {
            CommonResponse<?> response = importRequestService.pushNewRequest(requestBody);
            if (response.isSuccess()) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UUID userID = UUID.fromString ((String) ((Map<String, Object>) auth.getPrincipal()).get("id"));
                historyClient.pushHistory(HistoryDTO.builder()
                        .title("Create new Goods received note")
                        .type("create".toUpperCase())
                        .agentId(requestBody.getWarehouseId())
                        .user(
                                UserDTO.builder().id(userID).build()
                        ).agentType("warehouse")
                        .description(new HashMap<>() {{
                            put("description", String.format("Goods received note was created with code: %s", requestBody.getImportRequestCode()));
                        }}).build());
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getImportRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(required = false) String warehouseName,
            @RequestParam(defaultValue = "createdDate", required = false) String orderBy,
            @RequestParam(defaultValue = "desc", required = false) String orderType
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            ImportRequestFilter filter = ImportRequestFilter.builder()
                    .orderBy(orderBy).orderType(orderType)
                    .warehouseName(warehouseName).build();
            CommonResponse<?> response = importRequestService.getImportRequestList(pageable, filter);
            return ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }
}
