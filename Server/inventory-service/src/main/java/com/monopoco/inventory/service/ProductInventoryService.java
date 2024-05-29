package com.monopoco.inventory.service;

import com.monopoco.common.model.CommonResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.service
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:50
 */
public interface ProductInventoryService {

    public CommonResponse<?> addProductIntoBin(String binBarcode, String productBarcode, Integer quantity, UUID poId);

    public CommonResponse<?> getProductInventory(String productName, UUID warehouseId, Pageable pageable);

    public CommonResponse<?> searchProductInWarehouseByName(String productName, Pageable pageable, UUID warehouseId);

    public CommonResponse<?> searchProductInWarehouseByBarcode(String barcode);

    public CommonResponse<?> getAllProductInBinBBinId(UUID binId);

    public CommonResponse<?> transferProduct(String binBarcodeFrom, String binBarcodeTo, String productBarcode, Integer quantity);

    public CommonResponse<?> pickProduct(String productBarCode, String binBarcode, Integer quantity);
}