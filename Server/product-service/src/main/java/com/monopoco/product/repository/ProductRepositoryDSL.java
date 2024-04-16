package com.monopoco.product.repository;

import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.response.PageResponse;
import com.monopoco.product.response.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryDSL {

    public PageResponse<List<ProductDTO>> searchOrder(ProductFilter filter, Pageable pageable);
}
