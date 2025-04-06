package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.shippingCost.dto.ShippingCostDTO;
import com.dev.manto_sagrado.service.ShippingCostClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shippingCost")
public class ShippingCostClientController {
    @Autowired
    ShippingCostClientService service;

    @PostMapping
    public ResponseEntity<String> calculateShippingCost(@RequestBody ShippingCostDTO requestBody) {
        return service.calculateShipping(requestBody);
    }
}