package com.monopoco.product.service;

import com.monopoco.product.entity.Product;
import com.monopoco.product.entity.ProductCategory;
import com.monopoco.product.filter.ProductFilter;
import com.monopoco.product.repository.ProductCategoryRepository;
import com.monopoco.product.repository.ProductRepository;
import com.monopoco.product.repository.ProductRepositoryDSL;
import com.monopoco.product.response.CommonResponse;
import com.monopoco.product.response.PageResponse;
import com.monopoco.product.response.model.DropDown;
import com.monopoco.product.response.model.ProductCategoryDTO;
import com.monopoco.product.response.model.ProductDTO;
import com.monopoco.product.util.CommonUtil;
import jakarta.transaction.Transactional;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.service
 * Author: hungdq
 * Date: 08/04/2024
 * Time: 15:49
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepositoryDSL productRepositoryDSL;

    @Override
    public CommonResponse<?> addNewCategory(ProductCategoryDTO categoryDTO) {

        ProductCategory productCategory = productCategoryRepository.findByIsDeletedIsFalseAndCategoryName(categoryDTO.getCategoryName())
                .orElse(null);
        if (productCategory == null) {
            productCategory = ProductCategory.builder()
                    .productCategoryId(CommonUtil.generateRandomUUID())
                    .categoryName(categoryDTO.getCategoryName())
                    .categoryDescription(categoryDTO.getCategoryDescription())
                    .build();

            productCategoryRepository.save(productCategory);
            return new CommonResponse<>().success("Tạo thành công loại hàng hóa");
        } else {
            return new CommonResponse<>().badRequest("Loại hàng hóa đã tồn tại");
        }
    }

    @Override
    public CommonResponse<?> addNewProduct(ProductDTO productDTO) {
        Product productSku = productRepository.findByIsDeletedIsFalseAndSku(
                productDTO.getSku()
        ).orElse(null);
        if (productSku != null) {
            return new CommonResponse<>().badRequest().data("Mã SKU đã tồn tại");
        }
        Product product = productRepository.findByIsDeletedIsFalseAndProductCodeOrProductName(
                productDTO.getProductCode(), productDTO.getProductName()
        ).orElse(null);
        if (product == null) {
            product = Product.builder()
                    .productId(CommonUtil.generateRandomUUID())
                    .productCategoryId(productDTO.getProductCategory().getKey())
                    .productName(productDTO.getProductName())
                    .productCode(productDTO.getProductCode())
                    .productDescription(productDTO.getProductDescription())
                    .isPacked(productDTO.getIsPacked())
                    .refrigerated(productDTO.getRefrigerated())
                    .reorderQuantity(productDTO.getReorderQuantity())
                    .packedDepth(productDTO.getPackedDepth())
                    .packedHeight(productDTO.getPackedHeight())
                    .packedWeight(productDTO.getPackedWeight())
                    .packedWidth(productDTO.getPackedWidth())
                    .unit(productDTO.getUnit())
                    .sku(productDTO.getSku())
                    .build();
            productRepository.save(product);
            return new CommonResponse<>().success("Tạo mới hàng hóa thành công");
        } else {
            return new CommonResponse<>().badRequest("Mã hàng hóa hoặc tên hàng hóa đã tồn tại");
        }
    }

    @Override
    public CommonResponse<?> getAllProducts(ProductFilter filter, Pageable pageable) {
        PageResponse<List<ProductDTO>> response = productRepositoryDSL.searchOrder(filter, pageable);
        return new CommonResponse<>().success().data(response);
    }

    @Override
    public CommonResponse<?> getDropDownCategory() {
        List<ProductCategory> categories = productCategoryRepository.findAllByIsDeletedIsFalse();
        if (categories == null) {
            categories = new ArrayList<>();
        }

        List<DropDown<UUID, String>> categoryDropDown = categories.stream()
                .map(category ->
                        new DropDown<UUID, String>(category.getProductCategoryId(), category.getCategoryName()))
                .toList();

        return new CommonResponse<>().success().data(new PageResponse<List<DropDown<UUID, String>>>().data(
                categoryDropDown
        ).dataCount(categoryDropDown.size()).pageSize(0).pageNumber(0));
    }

    @Override
    public CommonResponse<?> getDropDownUnit() {
        List<String> units = productRepository.getUnit();
        if (units == null) {
            units = new ArrayList<>();
        }
        return new CommonResponse<>().success().data(new PageResponse<List<String>>().data(
                units
        ).dataCount(units.size()).pageSize(0).pageNumber(0));
    }

    @Override
    public CommonResponse<?> deleteProduct(UUID productId) {
        Product product = productRepository.findByIsDeletedIsFalseAndProductId(productId).orElse(null);
        if (product != null) {
            product.setIsDeleted(true);
            return new CommonResponse<>().success().message("Xóa hàng hóa thành công");
        } else {
            return new CommonResponse<>().notFound("Không tìm thấy hàng hóa");
        }
    }

    @Override
    public CommonResponse<String> getCategoryDescription(UUID categoryId) {
        ProductCategory productCategory = productCategoryRepository.findByIsDeletedIsFalseAndProductCategoryId(categoryId)
                .orElse(ProductCategory.builder().categoryDescription("No description").build());
        return new CommonResponse<>().success().data(productCategory.getCategoryDescription());
    }
}
