package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductImageAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductsImageAdminRepository extends JpaRepository<ProductImageAdmin, Long> {
    Optional<List<ProductImageAdmin>> findAllByProduct(ProductAdmin product);
    int deleteAllByProduct(ProductAdmin product);
}
