package com.monopoco.supplier.service;

import com.monopoco.supplier.client.ProductClient;
import com.monopoco.supplier.entity.Supplier;
import com.monopoco.supplier.entity.SupplierProduct;
import com.monopoco.supplier.repository.SupplierProductRepository;
import com.monopoco.supplier.repository.SupplierRepository;
import com.monopoco.supplier.repository.SupplierRepositoryDSL;
import com.monopoco.supplier.response.CommonResponse;
import com.monopoco.supplier.response.PageResponse;
import com.monopoco.supplier.service.dto.DropDown;
import com.monopoco.supplier.service.dto.ProductDTO;
import com.monopoco.supplier.service.dto.SupplierDTO;
import com.monopoco.supplier.service.filter.SupplierFilter;
import com.monopoco.supplier.service.request.SupplierRequest;
import com.monopoco.supplier.util.CommonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:58
 */

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierProductRepository supplierProductRepository;

    @Autowired
    private SupplierRepositoryDSL supplierRepositoryDSL;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private Environment env;

    @Override
    public CommonResponse<?> createNewSupplier(SupplierRequest request) {
        Boolean isExist = supplierRepository.findByIsDeletedIsFalseAndSupplierName(request.getSupplierName()).isPresent();
        if (isExist) {
            return new CommonResponse<>().badRequest("Supplier Name is Existed");
        } else {
            Supplier supplier = Supplier.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .supplierName(request.getSupplierName())
                    .supplierNumber(request.getSupplierNumber())
                    .supplierAddress1(request.getSupplierAddress1())
                    .supplierAddress2(request.getSupplierAddress2())
                    .build();
            try {
                supplierRepository.save(supplier);
                return new CommonResponse<>().success().data(supplier.getId());
            } catch (Exception exception) {
                exception.printStackTrace();
                return new CommonResponse<>().errorCode("500");
            }
        }
    }

    @Override
    public CommonResponse<?> getAllSupplierInDropDown() {
        List<Supplier> suppliers = supplierRepository.findAllByIsDeletedIsFalse();
        List<DropDown<UUID, String>> dropDown = suppliers.stream().map(
                supplier -> new DropDown<UUID, String>(supplier.getId(), supplier.getSupplierName())
        ).toList();
        return new CommonResponse<>().success().data(
                new PageResponse<List<DropDown<UUID, String>>>().data(dropDown).dataCount(Long.valueOf(dropDown.size()))
        );
    }

    @Override
    public CommonResponse<PageResponse<?>> getAllSupplier(SupplierFilter filter, Pageable pageable) {
        PageResponse<List<SupplierDTO>> pageResponse = supplierRepositoryDSL.searchOrder(filter, pageable);
        return new CommonResponse<>().success().data(pageResponse);
    }

    @Override
    public CommonResponse<?> getSupplierById(UUID id) {
        Supplier supplier = supplierRepository.findByIsDeletedIsFalseAndId(id).orElse(null);
        if (supplier != null) {
            return new CommonResponse<>().success().data(SupplierDTO.builder()
                    .id(supplier.getId())
                    .supplierNumber(supplier.getSupplierNumber())
                    .supplierName(supplier.getSupplierName())
                    .supplierAddress2(supplier.getSupplierAddress2())
                    .supplierAddress1(supplier.getSupplierAddress1())
                    .lastModifiedDate(supplier.getLastModifiedDate())
                    .build());
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> deleteSupplierById(UUID id) {
        Supplier supplier = supplierRepository.findByIsDeletedIsFalseAndId(id).orElse(null);
        if (supplier != null) {
            supplier.setIsDeleted(true);
            return new CommonResponse<>().success("Supplier is deleted");
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> addProductToListSupplier(UUID productId, UUID supplierId) {
        SupplierProduct supplierProduct = supplierProductRepository.findByIsDeletedIsFalseAndProductIdAndSupplierId(
                productId, supplierId
        ).orElse(null);
        if (supplierProduct == null) {
            supplierProduct = SupplierProduct.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .productId(productId)
                    .supplierId(supplierId)
                    .build();
            try {
                supplierProductRepository.save(supplierProduct);
                return new CommonResponse<>().success().data(supplierProduct.getId());
            } catch (Exception exception) {
                exception.printStackTrace();
                return new CommonResponse<>().errorCode("500").isOk(false);
            }
        } else {
            return new CommonResponse<>().badRequest("Product Of Supplier Is Existed In System");
        }
    }

    @Override
    public CommonResponse<?> addProductToListSupplierBatch(List<UUID> productIds, UUID supplierId) {
        if (productIds != null) {
            List<?> res = productIds.stream().map(productId -> {
                CommonResponse response = addProductToListSupplier(productId, supplierId);
                if (response.isSuccess()) {
                    return response.getData();
                } else {
                    return response.getResult().getMessage();
                }
            }).toList();
            return new CommonResponse<>().success().data(res);
        } else {
            return new CommonResponse<>().badRequest();
        }

    }

    @Override
    public CommonResponse<?> getAllProductOfSupplier(UUID supplierId, Pageable pageable) {
        Page<SupplierProduct> supplierProducts = supplierProductRepository.findAllByIsDeletedIsFalseAndSupplierIdOrderByCreatedDateDesc(
                supplierId, pageable
        );
        List<ProductDTO> productDTOS = new ArrayList<>();
        supplierProducts.forEach(supplierProduct -> {
            try {
                ResponseEntity<CommonResponse<ProductDTO>> response = productClient.getProductById(supplierProduct.getProductId(),String.format("Bearer %s", env.getProperty("superToken")));
                if (response.getStatusCode().is2xxSuccessful()) {
                    productDTOS.add(Objects.requireNonNull(response.getBody()).getData());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        PageResponse<?> pageResponse = new PageResponse<>().pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .data(
                        productDTOS
                )
                .dataCount(supplierProducts.getTotalElements());
        return new CommonResponse<>().success().data(pageResponse);
    }

    @Override
    public CommonResponse<?> deleteProductFromSupplier(UUID productId, UUID supplierId) {
        SupplierProduct supplierProduct = supplierProductRepository.findByIsDeletedIsFalseAndProductIdAndSupplierId(
                productId,supplierId
        ).orElse(null);
        if (supplierProduct != null) {
            supplierProduct.setIsDeleted(true);
            return new CommonResponse<>().success("Product Is Deleted From Supplier").data(productId);
        } else {
            return new CommonResponse<>().notFound();
        }
    }

    @Override
    public CommonResponse<?> updateSupplier(UUID supplierId, SupplierRequest change) {
        Supplier supplier = supplierRepository.findByIsDeletedIsFalseAndId(supplierId).orElse(null);
        if (supplier != null) {
            supplier.setSupplierName(change.getSupplierName());
            supplier.setSupplierNumber(change.getSupplierNumber());
            supplier.setSupplierAddress1(change.getSupplierAddress1());
            supplier.setSupplierAddress2(change.getSupplierAddress2());
            return new CommonResponse<>().success().data(supplierId);
        } else {
            return new CommonResponse<>().notFound();
        }
    }
}
