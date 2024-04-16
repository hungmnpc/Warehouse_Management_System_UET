package com.monopoco.product.repository;

import com.monopoco.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

    Optional<ProductCategory> findByIsDeletedIsFalseAndProductCategoryId(UUID productCategoryId);

    Optional<ProductCategory> findByIsDeletedIsFalseAndCategoryName(String categoryName);

    List<ProductCategory> findAllByIsDeletedIsFalse();
}
