package com.monopoco.product.controller;

import com.monopoco.product.response.CommonResponse;
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

    @GetMapping("")
    public ResponseEntity<?> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = productService.getAllProducts(null, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
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
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
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
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
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
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
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
            return ResponseEntity.internalServerError().body("Server xảy ra lỗi");
        }
    }
}
