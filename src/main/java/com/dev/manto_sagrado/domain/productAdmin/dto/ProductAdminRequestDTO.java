package com.dev.manto_sagrado.domain.productAdmin.dto;

import com.dev.manto_sagrado.domain.productAdmin.Enum.KitType;
import com.dev.manto_sagrado.domain.productAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAdminRequestDTO {
    private long id;
    private String name;
    private String teamName;
    private String season;
    private KitType kitType;
    private String brand;
    private String description;
    private int quantity;
    private Double evaluation;
    private BigDecimal price;
    private Status status;

    public static ProductAdmin newProduct(ProductAdminRequestDTO product) {
        return ProductAdmin.builder()
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
                .build();
    }
}
