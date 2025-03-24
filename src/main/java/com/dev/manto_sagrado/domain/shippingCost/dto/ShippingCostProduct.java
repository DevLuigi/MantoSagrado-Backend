package com.dev.manto_sagrado.domain.shippingCost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCostProduct {
    private String id;
    private Integer width;
    private Integer height;
    private Integer length;
    private Integer weight;
    private Double insurance_value;
    private Integer quantity;
}
