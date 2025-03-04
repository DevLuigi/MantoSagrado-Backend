package com.dev.manto_sagrado.domain.product.dto;

import com.dev.manto_sagrado.domain.product.Enum.KitType;
import com.dev.manto_sagrado.domain.product.Enum.Status;
import com.dev.manto_sagrado.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private long id;
    private String name;
    private String teamName;
    private String season;
    private KitType kitType;
    private String brand;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Status status;

    public static Product newProduct(ProductRequestDTO product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .teamName(product.getTeamName())
                .season(product.getSeason())
                .kitType(product.getKitType())
                .brand(product.getBrand())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
