package com.monopoco.purchaseorder.service;

import com.monopoco.common.factory.HistoryFactory;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.ProductDTO;
import com.monopoco.common.model.purchaseorder.AssignedUser;
import com.monopoco.common.model.purchaseorder.Status;
import com.monopoco.common.model.user.UserDTO;
import com.monopoco.purchaseorder.client.ProductClient;
import com.monopoco.purchaseorder.client.SupplierClient;
import com.monopoco.purchaseorder.client.UserClient;
import com.monopoco.purchaseorder.client.WarehouseClient;
import com.monopoco.purchaseorder.client.dto.SupplierDTO;
import com.monopoco.purchaseorder.client.dto.WarehouseDTO;
import com.monopoco.purchaseorder.entity.PurchaseOrder;
import com.monopoco.purchaseorder.entity.PurchaseOrderDetail;
import com.monopoco.purchaseorder.entity.UserAssignedPO;
import com.monopoco.purchaseorder.kafka.PurchaseOrderProducer;
import com.monopoco.purchaseorder.repository.PurchaseOrderDetailRepository;
import com.monopoco.purchaseorder.repository.PurchaseOrderRepository;
import com.monopoco.purchaseorder.repository.PurchaseOrderRepositoryDSL;
import com.monopoco.purchaseorder.repository.UserAssignedPORepository;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDTO;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDetailDTO;
import com.monopoco.purchaseorder.service.filter.ProductFilter;
import com.monopoco.purchaseorder.service.filter.SearchPurchaseOrder;
import com.monopoco.purchaseorder.service.request.ProductPurchaseOrderRequest;
import com.monopoco.purchaseorder.service.request.PurchaseOrderRequest;
import com.monopoco.purchaseorder.util.CommonUtil;
import com.monopoco.purchaseorder.util.PrincipalUser;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.service
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 15:57
 */

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private PurchaseOrderRepositoryDSL purchaseOrderRepositoryDSL;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private Environment environment;


    @Autowired
    private WarehouseClient warehouseClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserAssignedPORepository userAssignedPORepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PurchaseOrderProducer purchaseOrderProducer;

    @Override
    public CommonResponse<?> createNewPurchaseOrder(PurchaseOrderRequest request) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        if (request.getPoCode() != null && !StringUtils.isEmpty(request.getPoCode())) {
            Boolean isExistPoCode = purchaseOrderRepository.findByIsDeletedIsFalseAndPoCode(request.getPoCode()).isPresent();
            if (isExistPoCode) {
                return new CommonResponse<>().badRequest("Purchase Order Code was existed");
            }
        } else {
            request.setPoCode(CommonUtil.generatePOCode());
        }
        if (request.getSupplierId() == null) {
            return new CommonResponse<>().badRequest("Supplier could not be null");
        }
        try {
            ResponseEntity<CommonResponse<SupplierDTO>> responseSupplier = supplierClient.getSupplierById(
                    request.getSupplierId(), String.format("Bearer %s", environment.getProperty("superToken"))
            );

            ResponseEntity<CommonResponse<WarehouseDTO>> responseWarehouse = warehouseClient.getWarehouseById(
                    request.getWarehouseId(), environment.getProperty("superTokenIncludeBearer")
            );
            if (responseSupplier.getStatusCode().is2xxSuccessful()) {
                PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                        .id(CommonUtil.generateRandomUUID())
                        .poCode(request.getPoCode())
                        .arrivalDate(request.getArrivalDate())
                        .inboundDate(request.getInboundDate())
                        .comment(request.getComment())
                        .status(Status.DRAFT)
                        .referenceNumber(request.getReferenceNumber())
                        .supplierId(request.getSupplierId())
                        .supplierName(responseSupplier.getBody().getData().getSupplierName())
                        .warehouseId(request.getWarehouseId())
                        .warehouseName(responseWarehouse.getBody().getData().getWarehouseName())
                        .build();
                purchaseOrderRepository.save(purchaseOrder);
                return new CommonResponse<>().success().data(purchaseOrder.getId()).history(
                        HistoryFactory.createHistoryEventPOST(
                                principalUser.getUserId(),
                                principalUser.getUsername(),
                                purchaseOrder.getId(),
                                "purchase order",
                                String.format("Created purchase order: %s", purchaseOrder.getPoCode())
                        )
                );
            } else {
                return new CommonResponse<>().errorCode("500").message("Supplier Server Error");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResponse<>().errorCode("500").message("Purchase Order Server Error");
        }
    }

    public CommonResponse<String> addProductToPurchaseOrder(ProductPurchaseOrderRequest request, UUID purchaseOrderId)  {
        PrincipalUser principalUser = CommonUtil.getRecentUser();

        if (request.getProductId() != null) {
            try {
                ResponseEntity<CommonResponse<ProductDTO>> productRes = productClient.getProductById(
                        request.getProductId(), environment.getProperty("superTokenIncludeBearer")
                );

                if (productRes.getStatusCode().is2xxSuccessful()) {
                    ProductDTO productDTO = productRes.getBody().getData();
                    PurchaseOrderDetail detail = purchaseOrderDetailRepository
                            .findByIsDeletedIsFalseAndProductIdAndPurchaseOrderId(
                                    request.getProductId(), purchaseOrderId
                            ).orElse(null);
                    if (detail != null) {
                        detail.setQuantity(
                                detail.getQuantity() != null ? detail.getQuantity() + request.getQuantity()
                                        : request.getQuantity()
                        );
                    } else {
                        detail = PurchaseOrderDetail.builder()
                                .id(CommonUtil.generateRandomUUID())
                                .productId(request.getProductId())
                                .purchaseOrderId(purchaseOrderId)
                                .quantity(request.getQuantity())
                                .build();
                        purchaseOrderDetailRepository.save(detail);
                    }

                    return new CommonResponse<>().success().data(detail.getPurchaseOrderId().toString())
                            .history(
                                    HistoryFactory.createHistoryEventUPDATE(
                                            principalUser.getUserId(),
                                            principalUser.getUsername(),
                                            purchaseOrderId,
                                            "purchase order",
                                            String.format("Added product %s to purchase order: %d %s",
                                                    productDTO.getProductName(), request.getQuantity(), productDTO.getUnit())
                                    )
                            );
                }
                return new CommonResponse<>().notFound();
            } catch (Exception exception) {
                return new CommonResponse<>().badRequest();
            }
        } else {
            return new CommonResponse<>().badRequest("Product is empty");
        }
    }

    @Override
    public CommonResponse<?> getAllPurchaseOrders(SearchPurchaseOrder filter, Pageable pageable) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        if (principalUser.getWarehouseId() != null) {
            filter.setWarehouseId(principalUser.getWarehouseId());
        }
        PageResponse<List<PurchaseOrderDTO>> pageResponse = purchaseOrderRepositoryDSL.searchOrder(
            filter, pageable
        );
        pageResponse.getData().forEach(purchaseOrderDTO -> {
            String employeeName = "";
            if (purchaseOrderDTO.getEmployeeId() != null) {
                try {
                    ResponseEntity<CommonResponse<UserDTO>> employee = userClient.getUserById(
                            purchaseOrderDTO.getEmployeeId(),
                            environment.getProperty("superTokenIncludeBearer")
                    );
                    if (employee.getStatusCode().is2xxSuccessful()) {
                        employeeName = employee.getBody().getData().getUserName();
                    }
                }catch (Exception exception) {
                    exception.printStackTrace();
                    employeeName = "Not found";
                }
            } else {
                employeeName = "Not assigned yet";
            }
            purchaseOrderDTO.setEmployeeName(employeeName);
        });
        return new CommonResponse<>().success().data(pageResponse);
    }

    @Override
    public CommonResponse<?> getPurchaseOrderById(UUID id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(id).orElse(null);
        if (purchaseOrder != null) {
            UserAssignedPO userAssignedPO = userAssignedPORepository.findByPoId(id).orElse(null);
            UserDTO userDTO = null;
            if (userAssignedPO != null) {
                try {
                    ResponseEntity<CommonResponse<UserDTO>> responseAuth = userClient.getUserById(
                            userAssignedPO.getUserId(),
                            environment.getProperty("superTokenIncludeBearer")
                    );
                    if (responseAuth.getStatusCode().is2xxSuccessful()) {
                        userDTO = responseAuth.getBody().getData();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    userDTO = null;
                }

            }
            return new CommonResponse<>().success().data(
                    PurchaseOrderDTO.builder()
                            .supplierId(purchaseOrder.getSupplierId())
                            .supplierName(purchaseOrder.getSupplierName())
                            .id(purchaseOrder.getId())
                            .comment(purchaseOrder.getComment())
                            .poCode(purchaseOrder.getPoCode())
                            .arrivalDate(purchaseOrder.getArrivalDate())
                            .inboundDate(purchaseOrder.getInboundDate())
                            .referenceNumber(purchaseOrder.getReferenceNumber())
                            .status(purchaseOrder.getStatus().toString())
                            .createdBy(purchaseOrder.getCreatedBy())
                            .createdDate(purchaseOrder.getCreatedDate())
                            .lastModifiedBy(purchaseOrder.getLastModifiedBy())
                            .lastModifiedDate(purchaseOrder.getLastModifiedDate())
                            .warehouseId(purchaseOrder.getWarehouseId())
                            .deadLineToStock(purchaseOrder.getDeadLineToStocked())
                            .warehouseName(purchaseOrder.getWarehouseName())
                            .employeeFullName(userDTO != null ? userDTO.getFirstName() + " " + userDTO.getLastName() : "Not assigned yet")
                            .employeeName(userDTO != null ? userDTO.getUserName() : "Not assigned yet")
                            .build()
            );
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> deleteAPurchaseOrder(UUID id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(id).orElse(null);
        if (purchaseOrder != null) {
            if (purchaseOrder.getStatus().equals(Status.DRAFT)) {
                purchaseOrder.setIsDeleted(true);
                return new CommonResponse<>().success().data(id);
            } else {
                return new CommonResponse<>().badRequest("Can not delete this purchase order");
            }
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> updateQuantityOfPurchaseOrderDetail(UUID purchaseOrderDetailId, Integer quantity) {
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailRepository.findByIsDeletedIsFalseAndId(
                purchaseOrderDetailId
        ).orElse(null);
        PrincipalUser user = CommonUtil.getRecentUser();
        if (purchaseOrderDetail != null) {
            long oldQuantity = purchaseOrderDetail.getQuantity();
            purchaseOrderDetail.setQuantity(Long.valueOf(quantity));
            ProductDTO productDTO = productClient.getProductById(purchaseOrderDetail.getProductId(), environment.getProperty("superTokenIncludeBearer"))
                    .getBody().getData();
            return new CommonResponse<>().success().data(purchaseOrderDetailId)
                    .history(HistoryFactory.createHistoryEventUPDATE(
                            user.getUserId(),
                            user.getUsername(),
                            purchaseOrderDetail.getPurchaseOrderId(),
                            "purchase order",
                            String.format("Update quantity product %s: %d %s --> %d %s", productDTO.getProductName(),
                                    oldQuantity, productDTO.getUnit(), quantity, productDTO.getUnit())

                    ));
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> getAllItemInPurchaseOrder(UUID purchaseOrderId, Pageable pageable, ProductFilter productFilter) {
        PageResponse<List<PurchaseOrderDetailDTO>> response =
                purchaseOrderRepositoryDSL.searchOrderProductDTO(
                        purchaseOrderId,
                        productFilter,
                        pageable
                );

        return new CommonResponse<>().success().data(response);
    }

    @Override
    public CommonResponse<?> changeStatus(Status status, UUID id) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(
                id
        ).orElse(null);
        PrincipalUser user = CommonUtil.getRecentUser();
        if (purchaseOrder != null) {
            Status oldStatus = purchaseOrder.getStatus();
            if (oldStatus.equals(Status.DRAFT) && purchaseOrderDetailRepository.countAllByIsDeletedIsFalseAndPurchaseOrderId(
                    id
            ) == 0) {
                return new CommonResponse<>().badRequest("Purchase order is empty.");
            }
            purchaseOrder.setStatus(status);
            if (status.equals(Status.RECEIVED_AND_REQUIRES_WAREHOUSING)) {
                purchaseOrder.setArrivalDate(LocalDate.now());
            }
            return new CommonResponse<>().success().data(id)
                    .history(
                            HistoryFactory.createHistoryEventUPDATE(
                                    user.getUserId(),
                                    user.getUsername(),
                                    id,
                                    "purchase order",
                                    String.format("Update status purchase order: %s --> %s", oldStatus.toString(), status.toString())
                            )

                    );
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> deletePurchaseOrderDetail(UUID id) {
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailRepository
                .findByIsDeletedIsFalseAndId(id).orElse(null);
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        if (purchaseOrderDetail != null) {
            ProductDTO product = ProductDTO.builder()
                    .productName("Undefined")
                    .build();
            try {
                ResponseEntity<CommonResponse<ProductDTO>> productResponse  = productClient.getProductById(
                        purchaseOrderDetail.getProductId(),
                        environment.getProperty("superTokenIncludeBearer")
                );
                if (productResponse.getStatusCode().is2xxSuccessful()) {
                    product = productResponse.getBody().getData();
                }
            } catch (Exception exception) {
                product = ProductDTO.builder()
                        .productName("Not founded")
                        .build();
            }
            purchaseOrderDetail.setIsDeleted(true);
            return new CommonResponse<>().success().history(
                    HistoryFactory.createHistoryEventDELETE(
                            principalUser.getUserId(),
                            principalUser.getUsername(),
                            purchaseOrderDetail.getPurchaseOrderId(),
                            "purchase order",
                            String.format("Delete product from purchase order: %s", product.getProductName())
                    )
            );
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> assignedPOForUser(AssignedUser assignedUser) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(
                assignedUser.getPoId()
        ).orElse(null);
        if (purchaseOrder != null) {
            if (purchaseOrder.getStatus().getStatus() > Status.RECEIVED_AND_REQUIRES_WAREHOUSING.getStatus()) {
                return new CommonResponse<>().badRequest("Purchase Order is stocking or stocked");
            } else {

                UserAssignedPO userAssignedPO = userAssignedPORepository.findByPoId(
                        assignedUser.getPoId()
                ).orElse(
                        null
                );

                UserDTO user = null;
                try {
                    ResponseEntity<CommonResponse<UserDTO>> userResponse = userClient.getUserById(
                            assignedUser.getUserId(),
                            environment.getProperty("superTokenIncludeBearer")
                    );
                    if (userResponse.getStatusCode().is2xxSuccessful()) {
                        user = userResponse.getBody().getData();
                    }
                } catch (Exception exception) {
                    return new CommonResponse<>().notFound("Not founded employee");
                }


                HistoryEvent historyEvent = null;
                if (userAssignedPO != null) {
                    //Change
                    if (userAssignedPO.getUserId() != assignedUser.getUserId()) {
                        userAssignedPO.setUserId(assignedUser.getUserId());
                        assert user != null;
                        historyEvent = HistoryFactory
                                .createHistoryEventUPDATE(
                                        principalUser.getUserId(),
                                        principalUser.getUsername(),
                                        assignedUser.getPoId(),
                                        "purchase order",
                                        String.format("Changed employee assigned: %s", user.getUserName())
                                );
                    }
                } else {
                    userAssignedPO = new UserAssignedPO(
                            CommonUtil.generateRandomUUID(),
                            assignedUser.getUserId(), assignedUser.getPoId()
                    );
                    assert user != null;
                    historyEvent = HistoryFactory
                            .createHistoryEventPOST(
                                    principalUser.getUserId(),
                                    principalUser.getUsername(),
                                    assignedUser.getPoId(),
                                    "purchase order",
                                    String.format("Assigned PO to employee: %s", user.getUserName())
                            );

                }
                userAssignedPORepository.save(userAssignedPO);
                return new CommonResponse<>().success().data(userAssignedPO.getId()).history(historyEvent);
            }
        }
        return new CommonResponse<>().notFound("Not founded purchase order");
    }

    @Override
    public CommonResponse<?> checkIsAssignedPO(UUID poId) {
        Boolean existed = userAssignedPORepository.existsByPoId(poId);
        return new CommonResponse<>().success().data(existed);
    }

    @Override
    public CommonResponse<?> changeDeadlineToStock(UUID poId, LocalDate deadline) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(poId).orElse(null);
        if (purchaseOrder == null) {
            return new CommonResponse<>().notFound("Not founded purchase order");
        }

        HistoryEvent historyEvent = HistoryFactory.createHistoryEventUPDATE(
                principalUser.getUserId(),
                principalUser.getUsername(),
                poId,
                "purchase order",
                String.format("Updated deadline to stock: %s", deadline.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
        );
        purchaseOrder.setDeadLineToStocked(deadline);
        return new CommonResponse<>().success().data(poId).history(historyEvent);
    }

    @Override
    public CommonResponse<?> getAllPOInAssignedToEmployee() {
        PrincipalUser user = CommonUtil.getRecentUser();
        if (user != null && user.getUserId() != null && user.getWarehouseId() != null) {
            PageResponse<List<PurchaseOrderDTO>> pageResponse =
                    purchaseOrderRepositoryDSL.searchPOForEmployee(user.getWarehouseId(), user.getUserId());
            return new CommonResponse<>().success().data(pageResponse);
        }
        return new CommonResponse<>().badRequest();
    }

    @Override
    public CommonResponse<?> getPurchaseOrderDetailByPoIdAndProductId(UUID poId, UUID productId) {
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailRepository.findByIsDeletedIsFalseAndProductIdAndPurchaseOrderId(
            productId, poId
        ).orElse(null);
        if (purchaseOrderDetail == null) {
            return new CommonResponse<>().notFound("Not founded item in purchase order");
        }
        try {
            ResponseEntity<CommonResponse<ProductDTO>> responseProduct = productClient.getProductById(
                    purchaseOrderDetail.getProductId(),
                    environment.getProperty("superTokenIncludeBearer")
            );

            if (responseProduct.getStatusCode().is2xxSuccessful()) {
                return new CommonResponse<>().success().data(
                        PurchaseOrderDetailDTO.builder()
                                .product(responseProduct.getBody().getData())
                                .purchaseOrderDetailId(purchaseOrderDetail.getId())
                                .purchaseOrderId(purchaseOrderDetail.getPurchaseOrderId())
                                .stockedQuantity(purchaseOrderDetail.getStockedQuanity())
                                .quantity(purchaseOrderDetail.getQuantity())
                                .build()
                );
            } else {
                return new CommonResponse<>().badRequest();
            }

        }catch (Exception exception) {
            return new CommonResponse<>().errorCode("500");
        }
    }

    @Override
    public CommonResponse<?> stockPurchaseOrder( UUID employeeID, String employeeName, UUID poId, UUID productId, Integer quantity) {
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailRepository
                .findByIsDeletedIsFalseAndProductIdAndPurchaseOrderId(
                        productId, poId
                ).orElse(null);

        boolean isStocked = true;
        if (purchaseOrderDetail != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIsDeletedIsFalseAndId(
                    purchaseOrderDetail.getPurchaseOrderId()
            ).orElse(null);
            if (purchaseOrder == null) {
                return new CommonResponse<>().notFound("Not founded purchase order");
            } else {
                purchaseOrder.setStatus(Status.WAREHOUSING);
                List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailRepository.findAllByIsDeletedIsFalseAndPurchaseOrderId(
                        purchaseOrder.getId()
                );

                for (PurchaseOrderDetail pod : purchaseOrderDetails) {
                    Long stockedQuantity = pod.getStockedQuanity() == null ? 0L : pod.getStockedQuanity();
                    if (pod.getId() != purchaseOrderDetail.getId()) {
                        if (pod.getQuantity() > stockedQuantity) {
                            isStocked = false;
                        }
                    }
                }
            }

            Long newStockedQuantity = (purchaseOrderDetail.getStockedQuanity() == null ? 0 : purchaseOrderDetail.getStockedQuanity()) + quantity;

            if (purchaseOrderDetail.getQuantity() > newStockedQuantity) {
                isStocked = false;
            }
            purchaseOrderDetail.setStockedQuanity((purchaseOrderDetail.getStockedQuanity() == null ? 0 : purchaseOrderDetail.getStockedQuanity()) + quantity);
            HistoryEvent historyEvent = null;
            if (isStocked) {
                purchaseOrder.setStatus(Status.STOCKED);
                historyEvent = HistoryFactory.createHistoryEventUPDATE(
                        employeeID,
                        employeeName, purchaseOrder.getId(),
                        "purchase order",
                        String.format("Purchase Order was stocked done")
                );
            }
            if (historyEvent != null) {
                purchaseOrderProducer.sendMessage(historyEvent);
            }
            return new CommonResponse<>().success().data(purchaseOrderDetail.getId());
        } else {
            return new CommonResponse<>().notFound("Not found purchase order detail");
        }
    }

}
