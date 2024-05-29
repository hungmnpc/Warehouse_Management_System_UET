package com.monopoco.inventory.controller;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.PostProductIntoBin;
import com.monopoco.common.model.inventory.PickProductPost;
import com.monopoco.inventory.service.ProductInventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * Project: Server
 * Package: com.monopoco.inventory.controller
 * Author: hungdq
 * Date: 13/05/2024
 * Time: 10:56
 */

@RequestMapping("/products")
@RestController
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService productInventoryService;



    @PostMapping("/loads")
    public ResponseEntity<?> loadProductInToBin(@RequestBody PostProductIntoBin request) {
        try {
            CommonResponse<?> response = productInventoryService.addProductIntoBin(
                    request.getBinBarcode(),
                    request.getProductBarcode(),
                    request.getQuantity(),
                    request.getPoId()
            );
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/pick")
    public ResponseEntity<?> pickingProduct(@RequestBody PickProductPost request) {
        try {
            CommonResponse<?> response = productInventoryService.pickProduct(
                    request.getProductBarcode(),
                    request.getBinBarcode(),
                    request.getQuantity()
            );
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductInWH(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(required = false) UUID warehouseId
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = productInventoryService.searchProductInWarehouseByName(
             productName, pageable, warehouseId
            );
            if (response.isSuccess()) {
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
    public ResponseEntity<?> getAllProductInventory (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(required = false) UUID warehouseId
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = productInventoryService.getProductInventory(
                    productName, warehouseId, pageable
            );
            if (response.isSuccess()) {
                return ok().body(response);
            } else{
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/bins/{binId}/products")
    public ResponseEntity<?> getProductInBin(
            @PathVariable UUID binId
    ) {
        try {
            CommonResponse<?> response = productInventoryService.getAllProductInBinBBinId(binId);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }
}
