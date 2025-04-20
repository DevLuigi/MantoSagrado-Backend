package com.dev.manto_sagrado.domain.client.dto;

import com.dev.manto_sagrado.domain.client.Enum.Gender;
import com.dev.manto_sagrado.domain.client.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {
    private long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;

    public static Client newUser(ClientRequestDTO user) {
        return Client.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .lastName(user.getLastName())
                .cpf(user.getCpf())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }
}
