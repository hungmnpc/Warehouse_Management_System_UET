package com.monopoco.product.service;

import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.response.CommonResponse;
import com.monopoco.product.response.model.ProductCategoryDTO;
import com.monopoco.product.response.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    public CommonResponse<?> addNewCategory(ProductCategoryDTO categoryDTO);

    public CommonResponse<?> addNewProduct(ProductDTO productDTO);

    public CommonResponse<?> getAllProducts(ProductFilter filter, Pageable pageable);

    public CommonResponse<?> getDropDownCategory();

    public CommonResponse<?> getDropDownUnit();

    public CommonResponse<?> deleteProduct(UUID productId);

    public CommonResponse<String> getCategoryDescription(UUID categoryId);
}
