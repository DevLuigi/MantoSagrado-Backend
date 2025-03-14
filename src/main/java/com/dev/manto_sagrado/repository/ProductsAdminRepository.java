package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsAdminRepository extends JpaRepository<ProductAdmin, Long> {
    List<ProductAdmin> findAllByOrderByCreatedAtDesc();
}
