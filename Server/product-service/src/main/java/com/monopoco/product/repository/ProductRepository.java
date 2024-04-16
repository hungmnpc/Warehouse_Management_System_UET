package com.monopoco.product.repository;


import com.monopoco.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIsDeletedIsFalseAndProductCodeOrProductName(String productCode, String productName);

    Optional<Product> findByIsDeletedIsFalseAndSku(String sku);

    Optional<Product> findByIsDeletedIsFalseAndProductId(UUID productId);

    @Query("select distinct po.unit from Product po")
    List<String> getUnit();
}
