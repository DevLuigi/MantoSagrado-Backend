package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.shippingCost.dto.ShippingCostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShippingCostClientService {
    final static String URL = "https://www.melhorenvio.com.br/api/v2/me/shipment/calculate";

    @Value("${token}")
    private String token;

    @Value("${email}")
    private String email;

    public ResponseEntity<String> calculateShipping(ShippingCostDTO requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aplicação " + email);

        HttpEntity<ShippingCostDTO> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> externalResponse = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Filtra os headers para remover qualquer header CORS da resposta externa
        HttpHeaders filteredHeaders = new HttpHeaders();
        externalResponse.getHeaders().forEach((key, value) -> {
            if (!key.toLowerCase().startsWith("access-control-")) {
                filteredHeaders.put(key, value);
            }
        });

        return new ResponseEntity<>(
                externalResponse.getBody(),
                filteredHeaders,
                externalResponse.getStatusCode()
        );
    }
}
