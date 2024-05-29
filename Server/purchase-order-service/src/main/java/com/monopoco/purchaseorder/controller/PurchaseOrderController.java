package com.monopoco.purchaseorder.controller;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.purchaseorder.AssignedUser;
import com.monopoco.common.model.purchaseorder.Status;
import com.monopoco.purchaseorder.service.PurchaseOrderService;
import com.monopoco.purchaseorder.service.filter.ProductFilter;
import com.monopoco.purchaseorder.service.filter.SearchPurchaseOrder;
import com.monopoco.purchaseorder.service.request.ProductPurchaseOrderRequest;
import com.monopoco.purchaseorder.service.request.PurchaseOrderRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.controller
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 16:30
 */

@RestController
@RequestMapping("/purchase_orders")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @PostMapping("")
    public ResponseEntity<?> createNewPO(@RequestBody PurchaseOrderRequest request) {
        try {
            CommonResponse<?> response = purchaseOrderService.createNewPurchaseOrder(request);
            if (response.isSuccess()) {
                return ok(response);
            } else if (response.getResult().getResponseCode().startsWith("5")) {
                return internalServerError().body(response);
            } else {
                return badRequest().body(response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @PostMapping("/{poId}/employees")
    public ResponseEntity<?> assignEmployee(
            @PathVariable UUID poId,
            @RequestBody UUID employeeId
    ) {
        try {
            if (employeeId != null) {
                AssignedUser assignedUser = new AssignedUser(
                        poId, employeeId
                );
                CommonResponse<?> response = purchaseOrderService.assignedPOForUser(
                        assignedUser
                );
                if (response.isSuccess()) {
                    return ok(response);
                } else {
                    return badRequest().body(response);
                }
            } else {
                return badRequest().body(new CommonResponse<>().badRequest("Employee is empty"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPurchaseOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "") String poCode,
            @RequestParam(defaultValue = "") String referenceNumber,
            @RequestParam(required = false) UUID warehouseId,
            @RequestParam(required = false) LocalDate arrivalDateTo,
            @RequestParam(required = false) LocalDate arrivalDateFrom,
            @RequestParam(required = false) List<Integer>status
            ) {
        try {

            SearchPurchaseOrder filter = SearchPurchaseOrder.builder()
                    .arrivalDateTo(arrivalDateTo)
                    .arrivalDateFrom(arrivalDateFrom)
                    .referenceNumber(referenceNumber)
                    .status(status)
                    .poCode(poCode)
                    .warehouseId(warehouseId)
                    .build();
            Pageable pageable = PageRequest.of(page, size);
            CommonResponse<?> response = purchaseOrderService.getAllPurchaseOrders(
                    filter, pageable
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

    @GetMapping("/employees")
    public ResponseEntity<?> getAllPurchaseOrderForEmployee(
    ) {
        try {

            CommonResponse<?> response = purchaseOrderService.getAllPOInAssignedToEmployee();
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseOrderById(
            @PathVariable UUID id
            ) {
        try {
            CommonResponse<?> response = purchaseOrderService.getPurchaseOrderById(id);
            if (response.isSuccess()) {
                return ok(response);
            } else {
                return notFound().build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrderById(
            @PathVariable UUID id
    ) {
        try {
            CommonResponse<?> response = purchaseOrderService.deleteAPurchaseOrder(id);
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

    @PostMapping("/{purchaseOrderId}/products")
    public ResponseEntity<?> addProductToPurchaseOrder(
            @PathVariable UUID purchaseOrderId,
            @RequestBody ProductPurchaseOrderRequest products
            ) {
        try {

            CommonResponse<?> response = purchaseOrderService.addProductToPurchaseOrder(
                    products, purchaseOrderId
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

    @GetMapping("/{purchaseOrderId}/products")
    public ResponseEntity<?> getAllItemOfPurchaseOrder(
            @PathVariable UUID purchaseOrderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999999") int size,
            @RequestParam(defaultValue = "") String productName
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            ProductFilter filter = ProductFilter
                    .builder()
                    .productName(productName)
                    .build();
            CommonResponse<?> response = purchaseOrderService.getAllItemInPurchaseOrder(
                    purchaseOrderId, pageable, filter
            );
            return ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{purchaseOrderId}/detail/{productId}")
    public ResponseEntity<?> getDetailItemPo(
            @PathVariable UUID purchaseOrderId,
            @PathVariable UUID productId
    ) {
        try {
            CommonResponse<?> response = purchaseOrderService.getPurchaseOrderDetailByPoIdAndProductId(
                    purchaseOrderId,
                    productId
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

    @PutMapping("/{purchaseOrderId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable UUID purchaseOrderId,
            @RequestBody Status newStatus
            ) {
        try {
            CommonResponse<?> response = purchaseOrderService.changeStatus(newStatus, purchaseOrderId);
            if (response.isSuccess()) {
                return ok(response);
            } else if (response.getResult().getResponseCode().equals("400")) {
                return badRequest().body(response);
            } else {
                return notFound().build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deletePurchaseOrderDetail(
            @PathVariable UUID id
    ) {
        try {
            CommonResponse<?> commonResponse = purchaseOrderService.deletePurchaseOrderDetail(
                    id
            );
            if (commonResponse.isSuccess()) {
                return ok(commonResponse);
            } else {
                return notFound().build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }

    @GetMapping("/{poId}/employees/check")
    public ResponseEntity<?> checkAssigned(@PathVariable UUID poId) {
        try {
            CommonResponse<?> response = purchaseOrderService.checkIsAssignedPO(poId);
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

    @PutMapping("/{purchaseOrderId}/deadline")
    public ResponseEntity<?> updateStatus(
            @PathVariable UUID purchaseOrderId,
            @RequestBody LocalDate deadline
    ) {
        try {
            CommonResponse<?> response = purchaseOrderService.changeDeadlineToStock(purchaseOrderId, deadline);
            if (response.isSuccess()) {
                return ok(response);
            } else if (response.getResult().getResponseCode().equals("400")) {
                return badRequest().body(response);
            } else {
                return notFound().build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }
}
