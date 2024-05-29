package com.monopoco.product.service;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.product.controller.body.BodyGetProduct;
import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.response.model.ProductCategoryDTO;
import com.monopoco.product.response.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    public CommonResponse<?> addNewCategory(ProductCategoryDTO categoryDTO);

    public CommonResponse<?> addNewProduct(ProductDTO productDTO);

    public CommonResponse<?> getAllProducts(ProductFilter filter, Pageable pageable, List<UUID> idNotIn);

    public CommonResponse<?> updateProduct(UUID productId, ProductDTO productDTO);

    public CommonResponse<?> getProductById(UUID id);

    public CommonResponse<?> getProductByBarcode(String barcode);

    public CommonResponse<?> getDropDownCategory();

    public CommonResponse<?> getDropDownUnit();

    public CommonResponse<?> deleteProduct(UUID productId);

    public CommonResponse<String> getCategoryDescription(UUID categoryId);
}
