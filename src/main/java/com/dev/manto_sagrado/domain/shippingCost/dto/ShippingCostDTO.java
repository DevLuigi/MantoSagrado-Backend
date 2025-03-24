package com.dev.manto_sagrado.domain.shippingCost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCostDTO {
    private CepFromDTO from;
    private CepToDTO to;
    private List<ShippingCostProduct> products;
    private ShippingCostOptionsDTO options;
    private String service;
}
