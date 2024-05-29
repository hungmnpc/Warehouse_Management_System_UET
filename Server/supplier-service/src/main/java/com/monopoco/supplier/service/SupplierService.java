package com.monopoco.supplier.service;

import com.monopoco.supplier.response.CommonResponse;
import com.monopoco.supplier.response.PageResponse;
import com.monopoco.supplier.service.filter.SupplierFilter;
import com.monopoco.supplier.service.request.SupplierRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:46
 */
public interface SupplierService {

    CommonResponse<?> createNewSupplier(SupplierRequest request);

    CommonResponse<?> getAllSupplierInDropDown();

    CommonResponse<PageResponse<?>> getAllSupplier(SupplierFilter filter, Pageable pageable);

    CommonResponse<?> getSupplierById(UUID id);

    CommonResponse<?> deleteSupplierById(UUID id);

    CommonResponse<?> addProductToListSupplier(UUID productId, UUID supplierId);

    CommonResponse<?> addProductToListSupplierBatch(List<UUID> productId, UUID supplierId);

    CommonResponse<?> getAllProductOfSupplier(UUID supplierId,  Pageable pageable);

    CommonResponse<?> deleteProductFromSupplier(UUID productId, UUID supplierId);

    CommonResponse<?> updateSupplier(UUID supplierId, SupplierRequest supplierRequest);
}