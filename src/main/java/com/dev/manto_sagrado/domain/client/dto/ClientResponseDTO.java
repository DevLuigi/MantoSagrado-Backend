package com.dev.manto_sagrado.domain.client.dto;

import com.dev.manto_sagrado.domain.client.Enum.Gender;
import com.dev.manto_sagrado.domain.client.entity.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientResponseDTO {
    private long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;

    public static ClientResponseDTO fromUser(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .password(client.getPassword())
                .name(client.getName())
                .lastName(client.getLastName())
                .cpf(client.getCpf())
                .birthDate(client.getBirthDate())
                .gender(client.getGender())
                .build();
    }
}
