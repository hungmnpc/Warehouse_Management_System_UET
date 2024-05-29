package com.monopoco.product.controller;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.response.model.ProductCategoryDTO;
import com.monopoco.product.response.model.ProductDTO;
import com.monopoco.product.service.ProductService;
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
 * Package: com.monopoco.product.controller
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 16:03
 */

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/categories")
    public ResponseEntity<?> addNewCategory(
            @RequestBody ProductCategoryDTO productCategoryDTO
            ) {
        try {
            CommonResponse<?> response = productService.addNewCategory(productCategoryDTO);
            return ok().body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addNewProduct(
            @RequestBody ProductDTO productDTO
    ) {
        try {
            CommonResponse<?> response = productService.addNewProduct(productDTO);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDTO productDTO
    ) {
        try {
            CommonResponse<?> response = productService.updateProduct(id, productDTO);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return notFound().build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(required = false) List<UUID> idNotIn,
            @RequestParam(defaultValue = "") String productName
            ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            ProductFilter filter = ProductFilter
                    .builder()
                    .productName(productName)
                    .build();
            CommonResponse<?> response = productService.getAllProducts(filter, pageable, idNotIn);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/categories/dropdown")
    public ResponseEntity<?> getDropDownCategory(
    ) {
        try {
            CommonResponse<?> response = productService.getDropDownCategory();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable UUID id) {
        try {
            CommonResponse response = productService.getProductById(id);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> getProductByBarcode(@PathVariable String barcode) {
        try {
            CommonResponse response = productService.getProductByBarcode(barcode);
            if (response.isSuccess()) {
                return ok().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/categories/description/{id}")
    public ResponseEntity<?> getDropDownCategory(
            @PathVariable UUID id
    ) {
        try {
            CommonResponse<?> response = productService.getCategoryDescription(id);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/units/dropdown")
    public ResponseEntity<?> getDropDownUnit(
    ) {
        try {
            CommonResponse<?> response = productService.getDropDownUnit();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable UUID id
            ) {
        try {
            CommonResponse<?> response = productService.deleteProduct(id);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }
}
