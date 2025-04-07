package com.dev.manto_sagrado.domain.user.dto;

import com.dev.manto_sagrado.domain.user.Enum.Gender;
import com.dev.manto_sagrado.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResponseDTO {
    private long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;

    public static UserResponseDTO fromUser(User user) {
        return UserResponseDTO.builder()
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
