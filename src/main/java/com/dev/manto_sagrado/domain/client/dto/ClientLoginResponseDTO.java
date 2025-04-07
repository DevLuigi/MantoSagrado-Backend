package com.dev.manto_sagrado.domain.client.dto;

import com.dev.manto_sagrado.domain.client.entity.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientLoginResponseDTO {
    private long id;
    private String email;

    public static ClientLoginResponseDTO fromUser(Client client) {
        return ClientLoginResponseDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .build();
    }
}
