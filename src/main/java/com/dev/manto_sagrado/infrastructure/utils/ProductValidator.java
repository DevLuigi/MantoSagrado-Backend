package com.dev.manto_sagrado.infrastructure.utils;

import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import com.dev.manto_sagrado.repository.ProductsAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private ProductsAdminRepository repository;

    public static boolean isValid(ProductAdmin product) {
        if (product == null) return false;

        if (product.getName() == null || product.getName().trim().isEmpty()) return false;

        if (product.getTeamName() == null || product.getTeamName().trim().isEmpty()) return false;

        if (product.getSeason() == null || product.getSeason().trim().isEmpty()) return false;

        if (product.getKitType() == null) return false;

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) return false;

        if (product.getQuantity() < 0) return false;

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) return false;

        if (product.getStatus() == null) return false;

        if(product.getEvaluation() == null) return  false;

        return true;
    }
}
