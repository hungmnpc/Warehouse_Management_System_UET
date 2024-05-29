package com.monopoco.inventory.service.imlp;

import com.monopoco.common.factory.HistoryFactory;
import com.monopoco.common.model.*;
import com.monopoco.common.model.inventory.PickEvent;
import com.monopoco.common.model.inventory.ProductBinInventoryDTO;
import com.monopoco.common.model.inventory.ProductBinInventoryInterface;
import com.monopoco.common.model.inventory.StockEvent;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDetailDTO;
import com.monopoco.common.model.warehouse.area.BinLocationDetail;
import com.monopoco.inventory.clients.ProductClient;
import com.monopoco.inventory.clients.PurchaseOrderClient;
import com.monopoco.inventory.clients.WarehouseClient;
import com.monopoco.inventory.entity.ProductBinInventory;
import com.monopoco.inventory.entity.ProductInventory;
import com.monopoco.inventory.kafka.InventoryProducer;
import com.monopoco.inventory.repository.ProductBinInventoryRepository;
import com.monopoco.inventory.repository.ProductInventoryRepository;
import com.monopoco.inventory.repository.ProductInventoryRepositoryDSL;
import com.monopoco.inventory.service.ProductInventoryService;
import com.monopoco.inventory.util.CommonUtil;
import com.monopoco.inventory.util.PrincipalUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.service.imlp
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:51
 */

@Service
@Transactional
@Slf4j
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private WarehouseClient warehouseClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ProductBinInventoryRepository productBinInventoryRepository;

    @Autowired
    private Environment env;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductInventoryRepositoryDSL productInventoryRepositoryDSL;

    @Autowired
    private PurchaseOrderClient purchaseOrderClient;

    @Autowired
    private InventoryProducer inventoryProducer;


    @Override
    public CommonResponse<?> addProductIntoBin(String binBarcode, String productBarcode, Integer quantity, UUID poId) {
        try {
            BinLocationDetail binLocationDetail;
            ProductDTO productDTO;

            PrincipalUser principalUser = CommonUtil.getRecentUser();
            try {
                ResponseEntity<CommonResponse<BinLocationDetail>> binResponse = warehouseClient.getBinLocationByBarcode(
                        binBarcode, principalUser.getWarehouseId(), env.getProperty("superTokenWithBeare")
                );
                if (binResponse.getStatusCode().is2xxSuccessful() && binResponse.getBody().isSuccess()) {
                    binLocationDetail = binResponse.getBody().getData();
                } else {
                    return binResponse.getBody();
                }
            } catch (Exception e) {
                return new CommonResponse<>().notFound("Not founded bin");
            }
            try {
                ResponseEntity<CommonResponse<ProductDTO>> productResponse = productClient.getProductByBarcode(
                        productBarcode, env.getProperty("superTokenWithBeare")
                );
                if (productResponse.getStatusCode().is2xxSuccessful() && productResponse.getBody().isSuccess()) {
                    productDTO = productResponse.getBody().getData();
                } else {
                    return productResponse.getBody();
                }
            } catch (Exception exception) {
                return new CommonResponse<>().notFound("Not founded product");
            }

            if (productDTO != null && binLocationDetail != null && poId != null) {
                PurchaseOrderDetailDTO purchaseOrderDetail;
                try {
                    ResponseEntity<CommonResponse<PurchaseOrderDetailDTO>> poDetailResponse =
                            purchaseOrderClient.getDetailItemPo(
                                    poId, productDTO.getProductId(),
                                    env.getProperty("superTokenWithBeare")
                            );
                    if (poDetailResponse.getStatusCode().is2xxSuccessful()) {
                        purchaseOrderDetail = poDetailResponse.getBody().getData();
                    } else {
                        return new CommonResponse<>().badRequest();
                    }
                } catch (Exception e) {
                    return new CommonResponse<>().notFound("Not founded purchase order product");
                }
                ProductBinInventory productBinInventory = productBinInventoryRepository
                        .findByBinIdAndProductId(
                                binLocationDetail.getBinLocation().getBinId(),
                                productDTO.getProductId()).orElse(
                                        new ProductBinInventory(
                                                CommonUtil.generateRandomUUID(),
                                                productDTO.getProductId(),
                                                productDTO.getProductName(),
                                                binLocationDetail.getBinLocation().getBarcode(),
                                                binLocationDetail.getBinLocation().getBinId(),
                                                0
                                        )
                        );

                long remainingQuantity = purchaseOrderDetail.getQuantity() - (purchaseOrderDetail.getStockedQuantity() == null ? 0 : purchaseOrderDetail.getStockedQuantity());
                if (quantity > remainingQuantity) {
                    return new CommonResponse<>().badRequest("Exceed the quantity needed in stock.Quantity must lower " + (remainingQuantity));
                }

                if (!binLocationDetail.getBinLocation().getIsMultipleProduct()) {
                    if (binLocationDetail.getBinConfigurationDTO().getOnlyProductId() != null) {
                        if (!binLocationDetail.getBinConfigurationDTO().getOnlyProductId().toString().equals(
                                productDTO.getProductId().toString()
                        )) {
                            return new CommonResponse<>().badRequest("That Bin not available for this product");
                        } else if (productBinInventory.getQuantity() + quantity >
                                (binLocationDetail.getBinConfigurationDTO().getMaxStorage() == null ? -1 : binLocationDetail.getBinConfigurationDTO().getMaxStorage()) ) {
                            return new CommonResponse<>().badRequest("Exceeded maximum bin storage amount");
                        }
                    }
                }

                ProductInventory productInventory = productInventoryRepository.findByIsDeletedIsFalseAndProductIdAndWarehouseId(
                        productDTO.getProductId(),
                        binLocationDetail.getArea().getWarehouseId()
                ).orElse(null);
                if (productInventory == null) {
                    productInventory = new ProductInventory(
                            CommonUtil.generateRandomUUID(),
                            productDTO.getProductId(),
                            quantity.longValue(),
                            0L, null, productDTO.getReorderQuantity().longValue(),
                            binLocationDetail.getArea().getWarehouseId()
                    );
                } else {
                    productInventory.setQuantityAvailable(
                            productInventory.getQuantityAvailable() + quantity
                    );
                }
                productBinInventory.setQuantity(productBinInventory.getQuantity() + quantity);
                productInventoryRepository.save(productInventory);
                productBinInventoryRepository.save(productBinInventory);
                inventoryProducer.sendStockMessage(new StockEvent(
                        principalUser.getUserId(), principalUser.getUsername(),
                        poId, productDTO.getProductId(), quantity, binLocationDetail.getBinLocation().getBinId()
                ));

            } else {
                return new CommonResponse<>().badRequest();
            }
            log.error("{}", binLocationDetail.toString());
            log.error("{}", productDTO.toString());
            return new CommonResponse<>().success().data("Stocked successfully");
        }catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResponse<>().errorCode("500").isOk(false);
        }
    }

    @Override
    public CommonResponse<?> getProductInventory(String productName, UUID warehouseId, Pageable pageable) {

        PageResponse<List<ProductInventoryDTO>> productInventoryDTOS = productInventoryRepositoryDSL.searchOrder(
                productName, warehouseId, pageable
        );
        return new CommonResponse<>().success().data(productInventoryDTOS);
    }

    @Override
    public CommonResponse<?> searchProductInWarehouseByName(String productName, Pageable pageable , UUID warehouseId) {
        PrincipalUser principalUser = CommonUtil.getRecentUser();
        if (principalUser.getWarehouseId() != null) {
            warehouseId = principalUser.getWarehouseId();
        }

            PageResponse<List<ProductInventoryDTO>>  pageResponse = productInventoryRepositoryDSL.searchOrder(
                    productName, warehouseId, pageable
            );
            if (pageResponse != null && pageResponse.getData() != null) {
                UUID finalWarehouseId = warehouseId;
                pageResponse.getData().forEach(data -> {
                    List<ProductBinInventoryInterface> productBinInventories = productBinInventoryRepository.
                            getAllProductInBinWarehouseByProductName(finalWarehouseId, data.getProductId());
                    data.setDetail(productBinInventories);
                    System.out.println("debug");
                });

            }
            return new CommonResponse<>().success().data(pageResponse);
    }

    @Override
    public CommonResponse<?> searchProductInWarehouseByBarcode(String barcode) {
        return null;
    }

    @Override
    public CommonResponse<?> getAllProductInBinBBinId(UUID binId) {

        List<ProductBinInventory> productBinInventories = productBinInventoryRepository
                .findAllByBinId(binId);

        List<ProductBinInventoryDTO> productBinInventoryDTOS = productBinInventories.stream().map(
                a -> ProductBinInventoryDTO.builder()
                        .id(a.getId())
                        .binName(a.getBinName())
                        .binId(a.getBinId())
                        .productId(a.getProductId())
                        .productName(a.getProductName())
                        .quantity(a.getQuantity())
                        .build()
        ).toList();

        return new CommonResponse<>().success().data(productBinInventoryDTOS);
    }

    @Override
    public CommonResponse<?> transferProduct(String binBarcodeFrom, String binBarcodeTo, String productBarcode, Integer quantity) {
        return null;
    }

    @Override
    public CommonResponse<?> pickProduct(String productBarCode, String binBarcode, Integer quantity) {

        PrincipalUser user = CommonUtil.getRecentUser();
        if (user != null && user.getWarehouseId() != null) {
            BinLocationDetail binLocationDetail = null;
            ProductDTO product = null;
            try {
                ResponseEntity<CommonResponse<BinLocationDetail>> binResponse = warehouseClient.getBinLocationByBarcode(binBarcode, user.getWarehouseId(),
                        env.getProperty("superTokenWithBeare"));
                if (binResponse.getStatusCode().is2xxSuccessful()) {
                    binLocationDetail = binResponse.getBody().getData();
                }
            } catch (Exception exception) {
                return new CommonResponse<>().notFound("Not founded bin location");
            }

            try {
                ResponseEntity<CommonResponse<ProductDTO>> productResponse = productClient.getProductByBarcode(productBarCode,
                        env.getProperty("superTokenWithBeare"));
                if (productResponse.getStatusCode().is2xxSuccessful()) {
                    product = productResponse.getBody().getData();
                }
            } catch (Exception exception) {
                return new CommonResponse<>().notFound("Not founded product");
            }

            if (product != null && binLocationDetail != null) {
                ProductBinInventory productBinInventory = productBinInventoryRepository.findByBinIdAndProductId(
                        binLocationDetail.getBinLocation().getBinId(), product.getProductId()
                ).orElse(null);
                if (productBinInventory == null) {
                    return new CommonResponse<>().badRequest(String.format("There is no more product '%s' sin bin '%s'", product.getProductName(), binLocationDetail.getBinLocation().getBarcode()));
                } else {
                    if (productBinInventory.getQuantity() < quantity) {
                        return new CommonResponse<>().badRequest(String.format("The quantity taken exceeds the quantity in the bin, quantity available: %d", productBinInventory.getQuantity()));
                    } else {
                        Integer newQuantity = productBinInventory.getQuantity() - quantity;
                        if (newQuantity == 0) {
                            productBinInventoryRepository.delete(productBinInventory);
                        } else {
                            productBinInventory.setQuantity(newQuantity);
                        }
                        HistoryEvent historyEvent = HistoryFactory.createHistoryEventUPDATE(
                                user.getUserId(),
                                user.getUsername(),
                                user.getWarehouseId(),
                                "warehouse",
                                String.format("Pick %d %s `%s` from bin `%s`", quantity, product.getUnit(), product.getProductName(), binBarcode)
                        );

                        inventoryProducer.sendPickMessage(new PickEvent(
                                product.getProductId(),
                                binLocationDetail.getBinLocation().getBinId(),
                                quantity,
                                user.getWarehouseId()

                        ));

                        return new CommonResponse<>().success().data("Successfully picked product").history(historyEvent);
                    }

                }
            } else {
                return new CommonResponse<>().badRequest("Not founded resource. Try again.");
            }

        } else {
            return new CommonResponse<>().badRequest("Account have not permission to do that");
        }
    }
}
