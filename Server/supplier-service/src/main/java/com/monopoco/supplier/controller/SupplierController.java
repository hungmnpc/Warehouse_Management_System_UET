package com.monopoco.supplier.controller;

import com.monopoco.supplier.response.CommonResponse;
import com.monopoco.supplier.response.PageResponse;
import com.monopoco.supplier.service.SupplierService;
import com.monopoco.supplier.service.filter.SupplierFilter;
import com.monopoco.supplier.service.request.SupplierRequest;
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
 * Package: com.monopoco.supplier.controller
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 20:30
 */

@RestController
@RequestMapping("/suppliers")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @PostMapping("")
    public ResponseEntity<?> createNewSupplier(@RequestBody SupplierRequest request) {
        try {
            CommonResponse<?> response = supplierService.createNewSupplier(request);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable UUID id, @RequestBody SupplierRequest request) {
        try {
            CommonResponse<?> response = supplierService.updateSupplier(id, request);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable UUID id) {
        try {
            CommonResponse<?> response = supplierService.deleteSupplierById(id);
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
    public ResponseEntity<?> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "") String supplierName,
            @RequestParam(defaultValue = "") String supplierNumber
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            SupplierFilter filter = SupplierFilter.builder()
                    .supplierName(supplierName)
                    .supplierNumber(supplierNumber)
                    .build();
            CommonResponse<PageResponse<?>> response = supplierService.getAllSupplier(filter, pageable);
            return ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable UUID id) {
        try {
            CommonResponse<?> response = supplierService.getSupplierById(id);
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

    @GetMapping("/dropdown")
    public ResponseEntity<?> getSuppliersLikeDropDown() {
        try {
            CommonResponse<?> response = supplierService.getAllSupplierInDropDown();
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

    @PostMapping("/{id}/products")
    public ResponseEntity<?> addProductToSupplier(@PathVariable UUID id, @RequestBody UUID productId) {
        try {
            CommonResponse<?> response = supplierService.addProductToListSupplier(
                    productId, id
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
    @PostMapping("/{id}/products/batch")
    public ResponseEntity<?> addProductToSupplierBatch(@PathVariable UUID id, @RequestBody List<UUID> productIds) {
        try {
            CommonResponse<?> response = supplierService.addProductToListSupplierBatch(
                    productIds, id
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

    @GetMapping("/{supplierId}/products")
    public ResponseEntity<?> getProductFromSupplier(
            @PathVariable UUID supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size
    ) {
        try {

            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = supplierService.getAllProductOfSupplier(supplierId, pageable);
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
