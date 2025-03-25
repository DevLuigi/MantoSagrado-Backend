package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.shippingCost.dto.ShippingCostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shippingCost")
public class ShippingCostClientController {
    @Value("${token}")
    private String token;

    @Value("${email}")
    private String email;

    @PostMapping()
    public ResponseEntity<String> calculateShippingCost(@RequestBody ShippingCostDTO requestBody) {
        String url = "https://www.melhorenvio.com.br/api/v2/me/shipment/calculate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aplicação " + email);

        HttpEntity<ShippingCostDTO> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }
}