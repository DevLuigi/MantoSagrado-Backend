package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.product.Enum.KitType;
import com.dev.manto_sagrado.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByCreatedAtDesc();

//    boolean existsByNameAndSeasonAndKitType(String name, String season, KitType kitType);
}
