package com.dev.manto_sagrado.infrastructure.utils;

import com.dev.manto_sagrado.domain.product.entity.Product;
import com.dev.manto_sagrado.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private ProductsRepository repository;

    public static boolean isValid(Product product) {
        if (product == null) return false;

        if (product.getName() == null || product.getName().trim().isEmpty()) return false;

        if (product.getTeamName() == null || product.getTeamName().trim().isEmpty()) return false;

        if (product.getSeason() == null || product.getSeason().trim().isEmpty()) return false;

        if (product.getKitType() == null) return false;

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) return false;

        if (product.getQuantity() < 0) return false;

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) return false;

        if (product.getStatus() == null) return false;

//        return !repository.existsByNameAndSeasonAndKitType(product.getName(), product.getSeason(), product.getKitType());
        return true;
    }
}
