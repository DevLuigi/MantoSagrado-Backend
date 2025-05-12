package com.dev.manto_sagrado.domain.order.dto;

import com.dev.manto_sagrado.domain.order.Enum.Status;
import lombok.Data;

@Data
public class OrderStatusDTO {
    private Status status;
}
