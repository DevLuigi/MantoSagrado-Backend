package com.dev.manto_sagrado.domain.productAdmin.dto;

import com.dev.manto_sagrado.domain.productAdmin.Enum.KitType;
import com.dev.manto_sagrado.domain.productAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductAdminResponseDTO {
    private long id;
    private String name;
    private String teamName;
    private String season;
    private KitType kitType;
    private String brand;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Double evaluation;
    private Status status;
    private LocalDateTime createdAt;

    public static ProductAdminResponseDTO fromProduct(ProductAdmin product) {
        return ProductAdminResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .teamName(product.getTeamName())
                .season(product.getSeason())
                .kitType(product.getKitType())
                .brand(product.getBrand())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .evaluation(product.getEvaluation())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
