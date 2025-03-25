package com.dev.manto_sagrado.domain.shippingCost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCostOptionsDTO {
    private boolean receipt;
    private boolean own_hand;
}
